package app.shared.data.views.view_architecture_design_v3.shared.views
import app.shared.data.model.UserLineList
import app.shared.data.ref.RefVal
import app.shared.data.views.view_architecture_design_v3.shared.{View, ViewParams, ViewReqParams, ViewResponse}

/**
  * Created by joco on 02/06/2018.
  */
object UserLineListsView_HolderObject {

  trait UserLineLists_View extends View

  case class ReqParams_UserLineLists_View(s:Int)
    extends ViewReqParams[UserLineLists_View]

  case class Response_UserLineLists_View(lists:List[RefVal[UserLineList]])
    extends ViewResponse[UserLineLists_View]

  trait ViewParamsInstanceUserLineListsView extends ViewParams[UserLineLists_View] {
    type ResponseType = Response_UserLineLists_View
    type RequestParametersType = ReqParams_UserLineLists_View
  }
}
