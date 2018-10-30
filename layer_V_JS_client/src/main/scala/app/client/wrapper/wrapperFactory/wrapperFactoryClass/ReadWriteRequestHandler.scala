package app.client.wrapper.wrapperFactory.wrapperFactoryClass
import app.client.wrapper.ReadRequest
import app.client.wrapper.wrapperFactory.wrapperFactoryClass.components._wrapper_reqHandlers.UpdateReqHandler
import app.shared.data.model.Entity.Entity
import io.circe.{Decoder, Encoder}

import scala.reflect.ClassTag

trait ReadWriteRequestHandler {
  this: ReactCompWrapperFactory =>

  def handleReadRequest[E <: Entity](rr: ReadRequest[E]): Unit = readRequestHandler.queRequest(rr)

  def handleUpdateReq[E <: Entity : ClassTag : Decoder : Encoder](er: UpdateRequest[E]): Unit = // mutates cache, rerenders page
    UpdateReqHandler.launchUpdateReq(cache, er, reRenderCurrentlyRoutedPageComp)
}
