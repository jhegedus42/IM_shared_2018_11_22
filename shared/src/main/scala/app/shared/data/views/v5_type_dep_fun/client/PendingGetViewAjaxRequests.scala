package app.shared.data.views.v5_type_dep_fun.client

import app.shared.data.views.v5_type_dep_fun.shared.views.View

import scala.reflect.ClassTag

/**
  * Created by joco on 08/07/2018.
  */
case class PendingGetViewAjaxRequests(renderEngine:ReactRenderEngine) {

  var pendingRequests : Set[GetViewAjaxRequest[_<:View]] = Set()

  var isRenderingOnGoing : Boolean = false

  def handleGetViewAjaxRequestCompleted[V<:View:ClassTag](ajaxGetViewReq: GetViewAjaxRequest[V]) = {
     pendingRequests=pendingRequests-ajaxGetViewReq // update mutable variable
    if(!isRenderingOnGoing && pendingRequests.isEmpty){
      println("PendingGetViewAjaxRequests calls renderEngine.render() in handleGetViewAjaxRequestCompleted()")
      renderEngine.render()
    }
    if(isRenderingOnGoing && pendingRequests.isEmpty){
      println("Illegal State ERROR, something is really not good, in handleGetViewAjaxRequestCompleted")
    }
  }


  def addPendingAJAXRequest[V<:View:ClassTag](ajaxGetViewReq: GetViewAjaxRequest[V]) = {
    pendingRequests=pendingRequests+ajaxGetViewReq
  }

  // event handlers for the ReactRenderEngine

  case class RenderingWillStartEventHandler() {
    def handleEvent() = {
      isRenderingOnGoing = true
    }
  }

  case class RenderingHasFinishedEventHandler() {
    def handleEvent() = {
      isRenderingOnGoing = false
    }
  }

  renderEngine.registerRenderingWillStartEventHandler(RenderingWillStartEventHandler())
  renderEngine.registerRenderingHasFinishedEventHandler(RenderingHasFinishedEventHandler())


}
