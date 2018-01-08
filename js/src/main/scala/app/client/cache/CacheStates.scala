package app.client.cache

import app.shared.model.entities.Entity.Entity
import app.shared.model.ref.{Ref, RefVal}

sealed abstract class EntityCacheVal[E <: Entity] {

  //    def getValue : Option[HasValue[E]] = ???
  def getValue: Option[Ready[E]] = this match {
    case _: Pending[E] => None
    case _: Failed[E]  => None
    case NotInCache( ref )     => None
    case NotYetLoaded( ref )   => None
    case g @ Loaded( refVal )  => Some( g )
    case g @ Updated( refVal ) => Some( g )
  }
  def isReady(): Boolean = false

  override def toString: String = pprint.apply( this ).plainText
}

abstract class Pending[E <: Entity] extends EntityCacheVal[E]

abstract class Failed[E <: Entity] extends EntityCacheVal[E]

abstract class Ready[E <: Entity]() extends EntityCacheVal[E] {
  def refVal: RefVal[E]
}

case class NotInCache[E <: Entity](val ref: Ref[E] ) extends EntityCacheVal[E]

case class NotYetLoaded[E <: Entity](val ref: Ref[E] ) extends EntityCacheVal[E]
// in cache but not yet loaded, not yet tried to load, nothing...
//how can this ever be ?

case class Loading[E <: Entity](val ref: Ref[E] ) extends Pending[E]

case class Updating[E <: Entity](val refVal: RefVal[E] ) extends Pending[E]
// writing

case class Loaded[E <: Entity](val refVal: RefVal[E] ) extends Ready[E] {

  override def isReady(): Boolean = true
}

case class Updated[E <: Entity](val refVal: RefVal[E] ) extends Ready[E] {
  override def isReady(): Boolean = true
}

case class UpdateFailed[E <: Entity](val refVal: RefVal[E], err: String ) extends Failed[E]

case class ReadFailed[E <: Entity](val ref: Ref[E], err: String ) extends Failed[E]
