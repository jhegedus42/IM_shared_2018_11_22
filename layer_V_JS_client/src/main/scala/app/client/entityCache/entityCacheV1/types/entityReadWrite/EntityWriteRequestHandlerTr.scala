package app.client.entityCache.entityCacheV1.types.entityReadWrite

import app.shared.data.model.Entity.Entity
import app.shared.data.ref.RefVal
import io.circe.{Decoder, Encoder}

import scala.reflect.ClassTag

trait EntityWriteRequestHandlerTr {

  def handleUpdateReq[E <: Entity: ClassTag: Decoder: Encoder](
      rv: RefVal[E]
    ): Unit // mutates cache, rerenders page
}
