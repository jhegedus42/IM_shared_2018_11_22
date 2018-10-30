package app.client.wrapper.reqHandler

import app.shared.data.model.Entity.Entity
import app.shared.data.ref.RefVal

private[wrapper] case class UpdateRequest[E <: Entity](rv: RefVal[E] )
