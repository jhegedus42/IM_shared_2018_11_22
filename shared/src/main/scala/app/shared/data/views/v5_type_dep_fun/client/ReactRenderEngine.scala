package app.shared.data.views.v5_type_dep_fun.client

/**
  * Created by joco on 08/07/2018.
  */
class ReactRenderEngine {

  var reactComponent : ReactComponent = ???
  def render() = ??? // TFH


  def registerRenderingWillStartEventHandler(
      RenderingWillStartEventHandler: PendingGetViewAjaxRequests#RenderingWillStartEventHandler
    ) = ???

  def registerRenderingHasFinishedEventHandler(
      RenderingHasFinishedEventHandler: PendingGetViewAjaxRequests#RenderingHasFinishedEventHandler
    ) = ???

}
