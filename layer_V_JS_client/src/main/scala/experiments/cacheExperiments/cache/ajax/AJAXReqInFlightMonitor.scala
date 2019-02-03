package experiments.cacheExperiments.cache.ajax

import app.shared.data.model.Entity.Entity
import experiments.cacheExperiments.cache.ReRenderTriggerer
import experiments.cacheExperiments.cache.ajax.AJAXGetEntityApi.{Completed__ReadEntity_AjaxCall, InFlight_ReadEntity}

/**
  * This is watching over the ajax requests and what they are doing.
  * If the number of ajax requests drops to zero then we trigger a re-render.
  *
  * // TODO3
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
