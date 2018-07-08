package app.shared.data.views.v5_type_dep_fun.client

/**
  * Created by joco on 08/07/2018.
  */
class ReactRenderEngine {

  type HasFinished=PendingGetViewAjaxRequests#RenderingHasFinishedEventHandler
  type WillStart=PendingGetViewAjaxRequests#RenderingWillStartEventHandler

  var reactComponent : ReactComponent = ???
  var renderingHasFinishedEventHandler:Option[HasFinished] = None

  var renderingWillStartEventHandler:Option[WillStart]=None

  def render() = {

    renderingWillStartEventHandler.map(v=>v.handleEvent())

    println(
             "this is the rendered string by ReactRenderEngine's render() method =\n\n"+
            reactComponent.getWhatToRender().string +"\n\n"
           )
    //
    renderingHasFinishedEventHandler.map(v=>v.handleEvent())
  }

  def setReactComponent(rc: ReactComponent)= this.reactComponent=rc


  def registerRenderingWillStartEventHandler(
      handler:WillStart
    ) = this.renderingWillStartEventHandler=Some(handler)

  def registerRenderingHasFinishedEventHandler(
      handler: HasFinished
    ) = this.renderingHasFinishedEventHandler=Some(handler)

}
