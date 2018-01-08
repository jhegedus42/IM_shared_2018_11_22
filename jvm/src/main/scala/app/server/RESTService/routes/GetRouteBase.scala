package app.server.RESTService.take3.routes

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MethodDirectives.get
import akka.http.scaladsl.server.directives.ParameterDirectives.parameters
import akka.http.scaladsl.server.directives.PathDirectives.path
import app.shared.data.model.Entity.Data

/**
  * Created by joco on 14/12/2017.
  */
trait GetRouteBase[E <: Data] extends RouteBase[E] {
  import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
  //65e87471a90f4c03a9ea33afa6a62d33 commit e52e5fda47938adae9537622d70f3a164ed0cda6 Sat Dec 16 20:12:35 EET 2017
 // put our own directives here ...
}
