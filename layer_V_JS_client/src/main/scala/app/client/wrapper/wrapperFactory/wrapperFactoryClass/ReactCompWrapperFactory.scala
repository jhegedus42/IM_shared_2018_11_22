package app.client.wrapper.wrapperFactory.wrapperFactoryClass

//import app.client.wrapper.cache.RequestProcessor.{VanillaPageComponent_ReactCompConstructor, WrappedPageComponent_ReactCompConstructor}
import app.client.wrapper.{EntityCache_MutableState, EntityReaderWriter, ReactCompWrapper}
import app.client.wrapper.wrapperFactory.wrapperFactoryClass.components._wrapper_reqHandlers.{ReadReqHandler, UpdateReqHandler}
import app.shared.data.model.Entity.Entity
import app.shared.data.ref.{Ref, RefVal}
import io.circe.{Decoder, Encoder}

import scala.reflect.ClassTag



case class ReactCompWrapperFactory() extends  ReadWriteRequestHandler with EntityReaderWriterFactory {

  override def createNewEntityReaderWriter: EntityReaderWriter = new EntityReaderWriter(entityReadWriteRequestHandler = this)

  //mutable - coz its a var

  val cache: EntityCache_MutableState = new EntityCache_MutableState(this) //mutable state
  // egyszerre csak 1 updateRequest futhat (fut=Future el van kuldve)

  private[wrapper] var currently_routed_page: Option[StateSettable] = None

  lazy val wrapper = new ReactCompWrapper( re = this, cm = cache )

  val readRequestHandler = new ReadReqHandler(cache, reRenderCurrentlyRoutedPageComp)

  //  private
  //  def clearCache = {
  //    cache.resetCache()
  //    reRenderCurrentlyRoutedPageComp()
  //  }

  def reRenderCurrentlyRoutedPageComp(): Unit = {
    val c: EntityReaderWriter = cache.getCacheMap
    println( "re render with cache: " + c )
    currently_routed_page.foreach( {
                                     s =>
                                       s.setState( c )
                                   } )
  }

}


