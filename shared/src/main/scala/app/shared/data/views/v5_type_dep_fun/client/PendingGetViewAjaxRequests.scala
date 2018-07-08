package app.shared.data.views.v5_type_dep_fun.client

import app.shared.data.views.v5_type_dep_fun.shared.views.View

import scala.reflect.ClassTag

/**
  * Created by joco on 08/07/2018.
  */
class PendingGetViewAjaxRequests(renderEngine:ReactRenderEngine) {
  def handleGetViewAjaxRequestCompleted[V<:View:ClassTag](ajaxGetViewReq: GetViewAjaxRequest[V]) = ???


  def addPendingAJAXRequest[V<:View:ClassTag](ajaxGetViewReq: GetViewAjaxRequest[V]) = ???

}
