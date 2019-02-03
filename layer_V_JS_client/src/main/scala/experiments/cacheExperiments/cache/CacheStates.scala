package experiments.cacheExperiments.cache

import app.shared.data.model.Entity.Entity
import app.shared.data.ref.{Ref, RefVal}

private[cache] object CacheStates {
  sealed trait CacheState[E <: Entity]
  case class Loading[E <: Entity](r: Ref[E] ) extends CacheState[E]
  case class Loaded[E <: Entity](r:  Ref[E], refVal: RefVal[E] ) extends CacheState[E]
}
