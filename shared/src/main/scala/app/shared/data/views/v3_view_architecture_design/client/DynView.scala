package app.shared.data.views.v3_view_architecture_design.client

import app.shared.data.views.v3_view_architecture_design.shared.views.common.ViewName

/**
  * Created by joco on 02/06/2018.
  */
object DynView {
  case class DynViewReqParams(viewName:ViewName,payloadAsJSON:String)
  case class DynViewResponse(viewName:ViewName,payloadAsJSON:String)
  //  def viewPayloadToDyn[V<:View]()
  //  def respToDyn()
}
