package app.client.entityCache.entityCacheV1.types.entityReadWrite

import app.client.entityCache.entityCacheV1.state.CacheStates.EntityCacheVal
import app.shared.data.model.Entity.Entity
import app.shared.data.ref.Ref

import scala.reflect.ClassTag

trait GetEntityProvider {
  def getEntity[E <: Entity: ClassTag](r: Ref[E] ): EntityCacheVal[E]
}
