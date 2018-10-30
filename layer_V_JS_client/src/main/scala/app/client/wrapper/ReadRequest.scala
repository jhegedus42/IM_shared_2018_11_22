package app.client.wrapper

import app.shared.data.model.Entity.Entity
import app.shared.data.ref.Ref

import scala.reflect.ClassTag

private [wrapper] case class ReadRequest[E <: Entity: ClassTag](ref: Ref[E] )
