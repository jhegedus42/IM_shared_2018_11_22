package app.client.wrapper.reqHandler

import app.client.wrapper.{ReactCompWrapper, ReadRequest}
import app.shared.data.model.Entity.Entity
import io.circe.{Decoder, Encoder}

import scala.reflect.ClassTag

private[wrapper] trait ReadWriteRequestHandler {
  this: ReactCompWrapper =>

  def handleReadRequest[E <: Entity](rr: ReadRequest[E]): Unit = readRequestHandler.queRequest(rr)

  def handleUpdateReq[E <: Entity : ClassTag : Decoder : Encoder](er: UpdateRequest[E]): Unit = // mutates cache, rerenders page
    UpdateReqHandler.launchUpdateReq(cache, er, reRenderCurrentlyRoutedPageComp)
}
