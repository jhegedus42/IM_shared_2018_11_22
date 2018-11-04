package app.client.entityCache.entityCacheV1

import app.shared.data.model.Entity.Entity
import app.shared.data.ref.Ref

import scala.reflect.ClassTag

private [entityCacheV1] case class ReadRequest[E <: Entity: ClassTag](ref: Ref[E] )
