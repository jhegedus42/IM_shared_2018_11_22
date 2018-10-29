package app.client.wrapper.cache

import app.client.wrapper.cache.entityCache.{EntityCache, EntityCacheVal, Loaded, Loading, NotYetLoaded, ReadFailed, Ready, UpdateFailed, Updated, Updating}
import app.shared.data.model.Entity.Entity
import app.shared.data.ref.{Ref, RefVal}

/**
  * f079711f_4c99b1ca
  *
  * @param cacheRoute
  */

private[wrapper] class _wrapper_EntityCache_MutableState(cacheRoute:CacheRoot) {
  private[this] var immutableEntityCacheMap: EntityCache = cacheRoute.getNewCacheMap
  // miért wrapped Map ?
  // mibe van becsomagolva ?

  def getCacheMap: EntityCache = immutableEntityCacheMap

  def resetCache(): Unit = immutableEntityCacheMap = cacheRoute.getNewCacheMap

  private[this] def updateCache[E <: Entity](key: Ref[E],
                                             cacheVal: EntityCacheVal[E],
                                             oldVal: EntityCacheVal[E]): Unit = {
    assert(immutableEntityCacheMap.map(key).equals(oldVal))
    val newCacheMap: Map[Ref[_ <: Entity], EntityCacheVal[_ <: Entity]] =
      immutableEntityCacheMap.map.updated(key, cacheVal)
    immutableEntityCacheMap = immutableEntityCacheMap.copy(map = newCacheMap)
  }

  object EntityStateChanger {

    def setNotYetLoaded[E <: Entity](ref: Ref[E]): NotYetLoaded[E] = {
      val res = NotYetLoaded(ref)
      immutableEntityCacheMap = immutableEntityCacheMap.copy(map = immutableEntityCacheMap.map.updated(ref, res))
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

    def setLoaded[E <: Entity](oldVal: Loading[E], newVal: RefVal[E]): Unit =
      updateCache(newVal.r, Loaded(newVal), oldVal)

    def setUpdated[E <: Entity](oldVal: Updating[E], newVal: RefVal[E]): Unit =
      updateCache(newVal.r, Updated(newVal), oldVal)


    def setReadFailed[E <: Entity](oldVal: Loading[E], errorMsg: String): Unit =
      updateCache(oldVal.ref, ReadFailed(oldVal.ref, err = errorMsg), oldVal)

    def setUpdateFailed[E <: Entity](oldVal: Updating[E], errorMsg: String): Unit =
      updateCache(oldVal.refVal.r,
                  UpdateFailed(oldVal.refVal, err = errorMsg),
                  oldVal)

  }

}