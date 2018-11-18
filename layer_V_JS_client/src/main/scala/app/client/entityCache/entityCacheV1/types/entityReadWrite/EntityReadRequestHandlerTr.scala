package app.client.entityCache.entityCacheV1.types.entityReadWrite

import app.shared.data.model.Entity.Entity
import app.shared.data.ref.Ref

import scala.reflect.ClassTag

trait EntityReadRequestHandlerTr {
  def handleReadRequest[E <: Entity: ClassTag](r: Ref[E] ): Unit
}
