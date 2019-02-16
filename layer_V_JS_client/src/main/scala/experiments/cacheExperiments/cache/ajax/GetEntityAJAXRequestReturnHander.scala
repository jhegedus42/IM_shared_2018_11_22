package experiments.cacheExperiments.cache.ajax

import app.shared.data.model.Entity.Entity
import experiments.cacheExperiments.cache.ReRenderTriggerer
import experiments.cacheExperiments.cache.ajax.AJAXGetEntityApi.{Completed__ReadEntity_AjaxCall, InFlight_ReadEntity}

/**
  * This is watching over the ajax requests and what they are doing.
  *
  * Calls: reRender in ReRenderTriggerer
  * In response to what :
  * If the number of ajax requests drops to zero then we trigger a re-render.
  *
  * // TODO3
  *
  * This re-render can start new ajax request-s to be launched.
  *
  * Created by:
  *
  * CacheInterface
  *
  */

private[cache] case class GetEntityAJAXRequestReturnHander() {
  // BACKLOG => handle timeout

  var reRenderTriggerer: Option[ReRenderTriggerer] = None

  // var list of ajax requests in flight



  // TODO1
  //
  // T : creating ReRenderTriggerer for the first time
  //     - Q : who creates the ReRenderTriggerer object (for the first time) ?
  //           - Q : what is needed for creating it ?
  //                 - A :
  //                       - Q : who can create that ?
  //                             - A : toBeCalledByComponentDidMount
  //
  //                       - Q : when can this be created ?
  //      -
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



}
