package app.client.cache

import app.client.cache
import app.client.cache.wrapper.{ ReadAndWriteRequestQue}
import app.shared.data.model.Entity.Entity
import app.shared.data.ref.{Ref, RefVal}

//mutable state
private[cache] class Cache(rc:ReadAndWriteRequestQue) {
  private[this] var wrappedMap = new CacheMap(executor = rc)

  def getCacheMap() = wrappedMap

  def resetCache() = wrappedMap = new CacheMap(executor = rc)

  def setNotYetLoaded[E <: Entity](ref: Ref[E]): NotYetLoaded[E] = {
    val res = NotYetLoaded(ref)
    wrappedMap = wrappedMap.copy(map = wrappedMap.map.updated(ref, res))
    res
  }

  def setLoading[E <: Entity](e: NotYetLoaded[E]): Loading[E] = {

    val res: Loading[E] = Loading(e.ref)
    updateCache(e.ref, res, e)
    res

  }

  def setUpdating[E <: Entity](oldVal: Ready[E],
                               newVal: RefVal[E]): Updating[E] = {
    val updatingTo = Updating(newVal)
    updateCache(oldVal.refVal.r, updatingTo, oldVal)
    updatingTo
  }

  def setLoaded[E <: Entity](oldVal: Loading[E], newVal: RefVal[E]) =
    updateCache(newVal.r, Loaded(newVal), oldVal)

  def setUpdated[E <: Entity](oldVal: Updating[E], newVal: RefVal[E]) =
    updateCache(newVal.r, Updated(newVal), oldVal)

  def updateCache[E <: Entity](key: Ref[E],
                               cacheVal: EntityCacheVal[E],
                               oldVal: EntityCacheVal[E]): Unit = {
    assert(wrappedMap.map(key).equals(oldVal))
    val newCacheMap: Map[Ref[_ <: Entity], EntityCacheVal[_ <: Entity]] =
      wrappedMap.map.updated(key, cacheVal)
    wrappedMap = wrappedMap.copy(map = newCacheMap)
  }

  def setReadFailed[E <: Entity](oldVal: Loading[E], errorMsg: String): Unit =
    updateCache(oldVal.ref, ReadFailed(oldVal.ref, err = errorMsg), oldVal)

  def setUpdateFailed[E <: Entity](oldVal: Updating[E], errorMsg: String): Unit =
    updateCache(oldVal.refVal.r,
                UpdateFailed(oldVal.refVal, err = errorMsg),
                oldVal)
}