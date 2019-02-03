package experiments.cacheExperiments.cache.ajax

import app.shared.data.model.Entity.Entity
import app.shared.data.ref.uuid.UUID
import app.shared.data.ref.{Ref, RefVal}

import scala.concurrent.Future

object AJAXApi {

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

  /**
  *
  * Who uses this ?
  * [[experiments.cacheExperiments.cache.EntityCacheMap.readEntity]]
  * Why ?
  *
  * What does this do ?
  *
  * It launches an AJAX request to get an entity from the server.
  *
  *
//    * @param ref
//    * @tparam E
  * @return
  */

}
