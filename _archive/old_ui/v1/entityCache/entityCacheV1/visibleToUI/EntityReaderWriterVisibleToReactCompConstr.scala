package app.client.entityCache.entityCacheV1.visibleToUI

import app.client.entityCache.entityCacheV1.state.CacheStates.EntityCacheVal
import app.shared.data.model.Entity.Entity
import app.shared.data.ref.{Ref, RefVal}
import io.circe.{Decoder, Encoder}
import scala.reflect.ClassTag


trait EntityReaderWriterVisibleToReactCompConstr {
  def getEntity[E <: Entity: ClassTag](r: Ref[E] ): EntityCacheVal[E]

  def updateEntity[E <: Entity: ClassTag: Decoder: Encoder](rv: RefVal[E] ): Unit

}

// INPROGRESS  in progress ... replace CacheState with this interface ...