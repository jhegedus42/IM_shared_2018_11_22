package app.shared.data.views.view_architecture_design_v3.client

import app.shared.data.views.view_architecture_design_v3.client.DynView.{DynViewReqParams, DynViewResponse}
import app.shared.data.views.view_architecture_design_v3.shared.{View, ViewResponse}

/**
  * Created by joco on 02/06/2018.
  */
object ViewCacheStates{

  sealed trait ViewCacheState[V<:View]

  case class CachePayload[V<:View](req: DynViewReqParams, resp:DynViewResponse){
    lazy val viewResponse :ViewResponse[V] = ???
  }


  case class Loading[V<:View](req: DynViewReqParams) extends ViewCacheState[V]

  case class FailedToLoad[V<:View](req: DynViewReqParams) extends ViewCacheState[V]

  case class Loaded[V<:View](pl:CachePayload[V]) extends ViewCacheState[V]

  case class Invalidated[V<:View](pl:CachePayload[V]) extends ViewCacheState[V]

  case class Refreshing[V<:View](pl:CachePayload[V]) extends ViewCacheState[V]

  case class FailedToRefresh[V<:View](pl:CachePayload[V]) extends ViewCacheState[V]



}
