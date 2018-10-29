package app.client.cache.wrapper

//import app.client.cache.RequestProcessor.{VanillaPageComponent_ReactCompConstructor, WrappedPageComponent_ReactCompConstructor}
import app.client.cache.entityCache.EntityCache
import app.client.cache.wrapper._wrapper_reqHandlers.{ReadReqHandler, UpdateReqHandler}
import app.shared.data.model.Entity.{Data, Entity}
import app.shared.data.ref.{Ref, RefVal}
import io.circe.{Decoder, Encoder}

import scala.reflect.ClassTag
case class ReadRequest[E <: Entity: ClassTag](ref: Ref[E] )

case class UpdateRequest[E <: Entity](rv: RefVal[E] )

trait StateSettable {
  def setState(c: EntityCache )
}

class CacheRoot {

  def getNewCacheMap=new EntityCache(cacheRoot =this) //mutable - coz its a var

  private[this] val cache: _wrapper_EntityCache_MutableState = new _wrapper_EntityCache_MutableState(this) //mutable state
  // egyszerre csak 1 updateRequest futhat (fut=Future el van kuldve)

  private[wrapper] var currently_routed_page: Option[StateSettable] = None

  lazy val wrapper = new ReactCompWrapper( re = this, cm = cache )

  private[wrapper] val readHandler = new ReadReqHandler(cache, reRenderCurrentlyRoutedPageComp _)

  def clearCache = {
    cache.resetCache()
    reRenderCurrentlyRoutedPageComp()
  }

  private[cache] def handleReadRequest[E <: Entity](rr: ReadRequest[E] ): Unit = readHandler.queRequest(rr)

  private[this] def reRenderCurrentlyRoutedPageComp(): Unit = {
    val c: EntityCache = cache.getCacheMap
    println( "re render with cache: " + c )
    currently_routed_page.foreach( {
      s =>
        s.setState( c )
    } )
  }

  private[cache] def handleUpdateReq[E<:Entity:ClassTag : Decoder: Encoder](er:UpdateRequest[E]) : Unit =  // mutates cache, rerenders page
    UpdateReqHandler.launchUpdateReq(cache, er, reRenderCurrentlyRoutedPageComp _ )
}


