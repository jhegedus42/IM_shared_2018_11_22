package app.client.wrapper.wrapperFactory.wrapperFactoryClass

import app.shared.data.model.Entity.Entity
import app.shared.data.ref.RefVal

case class UpdateRequest[E <: Entity](rv: RefVal[E] )
