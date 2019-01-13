package experiments.cacheExperiments.cache

import app.shared.SomeError_Trait
import app.shared.data.model.Entity.Entity
import app.shared.data.model.LineText
import app.shared.data.ref.uuid.UUID
import app.shared.data.ref.{Ref, RefVal}
import app.shared.rest.routes.crudRequests.GetEntityRequest
import app.testHelpersShared.data.TestEntities
import experiments.cacheExperiments.cache.AJAXApi.{Completed__ReadEntity_AjaxCall, InFlight_ReadEntity}
import experiments.cacheExperiments.cache.CacheStates.{CacheState, Loading}
import scalaz.\/

import scala.concurrent.{ExecutionContextExecutor, Future}

/**
  * This is used by the [[AJAXReqInFlightMonitor]] to cause a re-render
  * of the react tree if needed.
  *
  * This is created in [[experiments.cacheExperiments.components.RootComp]] by the
  *  [[experiments.cacheExperiments.components.RootComp.toBeCalledByComponentDidMount()]]
  *  method when component did mount (what that really means, is a mystery).
  *
  *  Some answer can be found in
  *  [[https://learn.co/lessons/react-component-mounting-and-unmounting]].
  *
  * @param triggerReRender
  */
case class ReRenderTriggerer(triggerReRender: Unit => Unit )

/**
  * This is watching over the ajax requests and what they are doing.
  * If the number of ajax requests drops to zero then we trigger a re-render.
  *
  * // TODO ^^^^
  *
  * This re-render can start new ajax request-s to be launched.
  *
  * Who uses this  and how ?
  *
  * This is used by many different types of Cache-s.
  *
  *
  */

object AJAXReqInFlightMonitor {

  var reRenderTriggerer: Option[ReRenderTriggerer] = None

  var inFlightEntityReadAjaxRequests: Set[InFlight_ReadEntity[_ <: Entity]] = Set()

  def addToInFlightReqList[E <: Entity](request: InFlight_ReadEntity[E] ): Unit = {
    val newVal = inFlightEntityReadAjaxRequests + request
    inFlightEntityReadAjaxRequests = newVal
  }

  /**
    *
    * This is an event handler function.
    *
    * Called by
    * [[experiments.cacheExperiments.cache.EntityCacheMap.handleAjaxReqReturned]]
    * it recieves an ajax request that has been sent and returned.
    *
    *
    * @param ajaxReq This handles the event when an ajax request has been completed.
    * @tparam E
    */
  def removeAjaxCallFromInFlightList[E <: Entity](ajaxReq: Completed__ReadEntity_AjaxCall[E] ): Unit = {
    val newVal = inFlightEntityReadAjaxRequests - ajaxReq.inFlight_ReadEntity
    inFlightEntityReadAjaxRequests = newVal
    if (inFlightEntityReadAjaxRequests.isEmpty) reRenderTriggerer.get.triggerReRender()
  }

}

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
      resultAsFuture: Future[Option[RefVal[E]]],
      futureUUID:     UUID = UUID.random())

  case class Completed__ReadEntity_AjaxCall[E <: Entity](
                                                        inFlight_ReadEntity: InFlight_ReadEntity[E])

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
    * @param ref
    * @tparam E
    * @return
    */


}

object CacheStates {
  sealed trait CacheState[E]
  case class Loading[E](r: Ref[E] ) extends CacheState[E]
  case class Loaded[E](r:  Ref[E], refVal: RefVal[E] ) extends CacheState[E]
}

/**
  * This is a map that contains cached Entities.
  * @tparam E
  */

class EntityCacheMap[E <: Entity]() {
  var map: Map[Ref[E], CacheState[E]] = Map()

  def launchReadAjax[E <: Entity](ref: Ref[E] ): Unit = {

    // QUESTION TODO => should we launch this future immediately or only after the render method has been
    // completed ?  => not yet, only if needed

    implicit def executionContext: ExecutionContextExecutor =
      scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
    import app.client.rest.commands.generalCRUD.GetEntityAJAX.getEntity
    import io.circe.generic.auto._

//    val ajaxCallAsFuture: Future[Option[RefVal[E]]] = .map(


    def extractRes( x:  \/[SomeError_Trait, RefVal[E]]  )
    x => {
      val lt: = x
      val res: Option[RefVal[E]]              = lt.toOption
      res
    }

    for {
      res<-getEntity[E]( ref )

    }

    val ajaxCall=InFlight_ReadEntity(ref, ajaxCallAsFuture)
    AJAXReqInFlightMonitor.addToInFlightReqList(ajaxCall)

    ajaxCallAsFuture.onComplete(_ => AJAXReqInFlightMonitor(Completed__ReadEntity_AjaxCall(ajaxCall)))
  }

  def readEntity(refToEntity: Ref[E] ): CacheState[E] = { // 74291aeb_02f0aea6
    if (!map.contains( refToEntity )) {
      val loading = Loading( refToEntity )

      AJAXApi.launchReadAjax(refToEntity)

      loading

    } else map( refToEntity )

    // TASK_19ffbc83_02f0aea6

  }
}

object Cache {

  // erre lehetni irni type class-okat: vmi altalanos getEntity
  // metodust, azaz pl. attol fuggoen h. milyen entity't ker a react comp mas instance hivodik meg...
  // de ezt majd irjuk meg azutan ha a konkret dolgok kesz vannak
  // cache will have a separate map for each entity
  // for each view
  // ezek adnak egy type safety-t, nem kell kasztolgatni, az is latszik tisztan, hogy milyen
  // entity-t vannak hasznalatban

}
