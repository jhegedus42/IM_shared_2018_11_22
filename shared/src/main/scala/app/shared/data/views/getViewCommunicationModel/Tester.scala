package app.shared.data.views.getViewCommunicationModel

import app.shared.data.views.getViewCommunicationModel.client.{
  AjaxInterface,
  Cache,
  PendingGetViewAjaxRequests,
  ReactComponent,
  ReactRenderEngine
}
import app.shared.data.views.getViewCommunicationModel.serverSide.akkaHttpWebServer.HttpServerOnTheInternet
import app.shared.data.views.getViewCommunicationModel.shared.views.View

object Tester extends App {

  println("start")
  testRender()

  def testRender() = {

    val server:          HttpServerOnTheInternet = HttpServerOnTheInternet()
    val ajaxInterface:   AjaxInterface           = AjaxInterface( server )
    val renderingEngine: ReactRenderEngine       = ReactRenderEngine()
    val pendingGetViewAjaxRequests: PendingGetViewAjaxRequests =
      PendingGetViewAjaxRequests( renderingEngine )
    val cache: Cache = Cache( ajaxInterface, pendingGetViewAjaxRequests )
    val reactComponent = ReactComponent( cache )
    renderingEngine.setReactComponent( reactComponent )
    renderingEngine.render()
    println("the main thread will sleep now for 20 sec")
    var counter=0
    do {
      Thread.sleep(1 * 1000)
      Thread.`yield`()
      println("yielding, "+counter)
      counter=counter+1
    } while(true)
    println("the main woke up - end of story")

  }

}
