package app.client.entityCache.entityCacheV1.reqHandler

import app.client.entityCache.entityCacheV1.{RootReactCompConstr_Enhancer, ReadRequest}
import app.shared.data.model.Entity.Entity
import io.circe.{Decoder, Encoder}

import scala.reflect.ClassTag

private[entityCacheV1] trait ReadWriteRequestHandler {
  this: RootReactCompConstr_Enhancer =>

  def handleReadRequest[E <: Entity](rr: ReadRequest[E]): Unit = readRequestHandler.queRequest(rr)

  def handleUpdateReq[E <: Entity : ClassTag : Decoder : Encoder](er: UpdateRequest[E]): Unit = // mutates cache, rerenders page
    UpdateReqHandler.launchUpdateReq(cache, er, reRenderCurrentlyRoutedPageComp)
}
