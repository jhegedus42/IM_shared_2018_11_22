package app.shared.data.views.v5_type_dep_fun

import app.shared.data.views.v5_type_dep_fun.client.{
  AjaxInterface,
  Cache,
  PendingGetViewAjaxRequests,
  ReactComponent,
  ReactRenderEngine
}
import app.shared.data.views.v5_type_dep_fun.serverSide.akkaHttpWebServer.HttpServerOnTheInternet
import app.shared.data.views.v5_type_dep_fun.shared.views.View

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
    Thread.sleep(20*1000)
    println("the main woke up - end of story")

  }

}
