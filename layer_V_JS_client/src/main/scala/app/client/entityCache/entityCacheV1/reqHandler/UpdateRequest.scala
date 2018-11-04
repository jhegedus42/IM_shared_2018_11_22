package app.client.entityCache.entityCacheV1.reqHandler

import app.shared.data.model.Entity.Entity
import app.shared.data.ref.RefVal

private[entityCacheV1] case class UpdateRequest[E <: Entity](rv: RefVal[E] )
