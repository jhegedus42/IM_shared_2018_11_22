package app.shared.data.views.v3_view_architecture_design.shared.views

import app.shared.data.views.v3_view_architecture_design.shared.views.common.{View, ViewParams, ViewReqParams, ViewResponse}

object View2_HolderObject {

  trait View2 extends View
  case class ReqParams_View2(s:Int) extends ViewReqParams[View2]
  case class Response_View2(s:String) extends ViewResponse[View2]

  trait ViewParamsInstanceView2 extends ViewParams[View2] {
    type ResponseType = Response_View2
    type RequestParametersType = ReqParams_View2
  }

}
