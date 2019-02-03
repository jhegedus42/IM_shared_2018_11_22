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
  */



object InFlightRequestsTracker {

  var reRenderTriggerer: Option[ReRenderTriggerer] = None
  // TODO1
  //
  // Q : who creates the ReRenderTriggerer object ?
  //     - Q : what is needed for creating it ?
  //           - A :
  //           - Q : who can create that ?
  //           - Q : when can this be created ?
  //
  // T : questions related to updating reRenderTriggerer
  //     - Q : when should this object be updated ?
  //     - Q : why should this object be update ?
  //           - A : when something changes in the state of the client
  //                 then it might be needed to update this
  //                 to make sure that the application continues to work correctly
  //                 - Q : for example ?
  //                       - A : rooted top page changes, MAYBE ? //TODO1
  //

  // When GetEntity AJAX call returns with "returnValue" then it calls
  // reactToGetEntityAJAXReqReturns(returnValue)

  // def reactToGetEntityAJAXReqReturns(returnValue) = {
  //   Cache.updateCacheInResponseToGetEntityAJAXReqReturned(returnValue)
  //   val newCacheValue= Cache.getCacheValue
  //   val res=do-we-need-to-re-render(newVal, someOtherMutableStateInClient)
  //   if (res) reRender() // ReRenderTriggerer
  // }


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
