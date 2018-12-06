package app.modelling_purely_for_modelling_not_to_be_used_in_real_app.getViewCommunicationModel

import app.modelling_purely_for_modelling_not_to_be_used_in_real_app.getViewCommunicationModel.client.{
  AjaxInterface,
  Cache,
  PendingGetViewAjaxRequests,
  ReactComponent,
  ReactRenderEngine
}
import app.modelling_purely_for_modelling_not_to_be_used_in_real_app.getViewCommunicationModel.serverSide.akkaHttpWebServer.HttpServerOnTheInternet
import app.modelling_purely_for_modelling_not_to_be_used_in_real_app.getViewCommunicationModel.shared.views.View

object Tester extends App {
  // Random UUID: eb1252385ca047bd87a13e3fe1f888a7
  // commit fc5bb550a0436ada8876f7c8a18d4b4bf9407091
  // Date: Sat Jul 28 10:53:41 EEST 2018



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
