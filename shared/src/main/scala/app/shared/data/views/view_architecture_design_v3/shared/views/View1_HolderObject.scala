package app.shared.data.views.view_architecture_design_v3.shared.views

import app.shared.data.views.view_architecture_design_v3.shared.{View, ViewParams, ViewReqParams, ViewResponse}

/**
  * Created by joco on 02/06/2018.
  */


object View1_HolderObject {

  trait View1 extends View
  case class Response_View1(s:String) extends ViewResponse[View1]
  case class ReqParams_View1(s:String) extends ViewReqParams[View1]

  trait ViewParamsInstanceView1 extends ViewParams[View1] {
    type ResponseType = Response_View1
    type RequestParametersType = ReqParams_View1
  }

}
