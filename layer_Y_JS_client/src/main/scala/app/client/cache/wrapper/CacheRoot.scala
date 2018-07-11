package app.client.cache.wrapper

//import app.client.cache.RequestProcessor.{VanillaPageComponent_ReactCompConstructor, WrappedPageComponent_ReactCompConstructor}
import app.client.cache.entityCache.{EntityCache, EntityCacheMap}
import app.client.cache.wrapper.reqHandlers.{ReadReqHandler, UpdateReqHandler}
import app.shared.data.model.Entity.{Data, Entity}
import app.shared.data.ref.{Ref, RefVal}
import io.circe.{Decoder, Encoder}

import scala.reflect.ClassTag
//todolater make this untyped ... RefDyn
case class ReadRequest[E <: Data: ClassTag](ref: Ref[E] )

case class UpdateRequest[E <: Data](rv: RefVal[E] )

trait StateSettable {
  def setState(c: EntityCacheMap )
}

class CacheRoot {

  def getNewCacheMap=new EntityCacheMap(cacheRoot =this) //mutable - coz its a var

  private[this] val cache: EntityCache = new EntityCache( this ) //mutable state
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
    val c: EntityCacheMap = cache.getCacheMap()
    println( "re render with cache: " + c )
    currently_routed_page.foreach( {
      s =>
        s.setState( c )
    } )
  }

  private[cache] def handleUpdateReq[E<:Entity:ClassTag : Decoder: Encoder](er:UpdateRequest[E]) : Unit =  // mutates cache, rerenders page
    UpdateReqHandler.launchUpdateReq(cache, er, reRenderCurrentlyRoutedPageComp _ )
}


