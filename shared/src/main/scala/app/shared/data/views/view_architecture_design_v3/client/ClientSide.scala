package app.shared.data.views.view_architecture_design_v3.client

import ViewCacheStates.ViewCacheState
import app.shared.data.views.view_architecture_design_v3.client.DynView.DynViewReqParams

/**
  * Created by joco on 02/06/2018.
  */
object ClientSide{


  object Cache{
    // cache Map[ViewPayLoadType]
    var map=Map[DynViewReqParams, ViewCacheState[_]]()



    object AjaxCall {
      // when returns, trigger's a re-render of react components
      // but which ones ?
      // all of them
      // so the cache needs to have a reference to the root component ...

    }

  }

}