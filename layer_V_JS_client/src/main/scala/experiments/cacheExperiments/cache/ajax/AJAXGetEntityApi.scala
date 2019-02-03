package experiments.cacheExperiments.cache.ajax

import app.shared.data.model.Entity.Entity
import app.shared.data.ref.uuid.UUID
import app.shared.data.ref.{Ref, RefVal}

import scala.concurrent.Future

object AJAXGetEntityApi {

  /**
    *
    * This describes an Entity Read AJAX request in flight.
    *
    * @param ref
    * @param resultAsFuture
    * @param futureUUID
    * @tparam E
    */
  case class InFlight_ReadEntity[E <: Entity](
      ref:            Ref[E],
      resultAsFuture: Future[RefVal[E]],
      futureUUID:     UUID = UUID.random())

  case class Completed__ReadEntity_AjaxCall[E <: Entity](inFlight_ReadEntity: InFlight_ReadEntity[E] )


}
