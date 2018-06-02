package app.shared.data.views.view_architecture_design_v3.client

import app.shared.data.views.view_architecture_design_v3.shared.ViewName

/**
  * Created by joco on 02/06/2018.
  */
object DynView {
  case class DynViewReqParams(viewName:ViewName,payloadAsJSON:String)
  case class DynViewResponse(viewName:ViewName,payloadAsJSON:String)
  //  def viewPayloadToDyn[V<:View]()
  //  def respToDyn()
}
