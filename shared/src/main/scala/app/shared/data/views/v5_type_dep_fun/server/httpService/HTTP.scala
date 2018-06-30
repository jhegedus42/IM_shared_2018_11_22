package app.shared.data.views.v5_type_dep_fun.server.httpService

import app.shared.data.views.v5_type_dep_fun.server.logic.ServerSideLogic.ServerLogicTypeClass
import app.shared.data.views.v5_type_dep_fun.shared.CirceUtils._
import app.shared.data.views.v5_type_dep_fun.shared.{GetViewHttpRouteName, HttpRouteName}
import app.shared.data.views.v5_type_dep_fun.shared.views.View1.{View1, View1_Par}
import app.shared.data.views.v5_type_dep_fun.shared.views.{View, View1}
import io.circe.generic.auto._
import io.circe.{Decoder, Encoder, Error}

import scala.reflect.ClassTag

case class JSON(string: String )

case class HttpServer(){
  val view1_routeHandler: HttpRouteHandler[View1]=
    new HttpRouteHandler[View1]()

  val view1_routeName: HttpRouteName=
    view1_routeHandler.getEndpointName()

  def serveRequests(routeName:HttpRouteName,
                       requestPayload:JSON):Option[JSON]={
    // case based on endpointName
    routeName.name match
    {
      case view1_routeName.name =>
        Some(view1_routeHandler.getView[View1](requestPayload))

      case _ => None
    }
  }



}

case class HttpRouteHandler[V<:View:ClassTag](){
  def getView[V <: View]
      (
        paramJSON: JSON
      )
      ( implicit
        decoder: Decoder[V#Par],
        encoder: Encoder[V#Res],
        serverLogic:ServerLogicTypeClass[V]
      ): JSON = {

    val r:   Either[Error, V#Par] = decodeJSONToPar[V](paramJSON)

    val par: V#Par                = r.right.get

    val res: V#Res = serverLogic.getView(par )

    JSON(encodeResToJSON[V](res))

    // todo print endpoint name

  }

  def getEndpointName():HttpRouteName=
    GetViewHttpRouteName.getViewHttpRouteName[V]()
}

object HttpEndpointTestDriver extends App {

  //we want an endopoint builder -parameterized on View - that
  // creates a class that contains a function f, that :


//  val par: View1_Par = View1.View1_Par("pina")

//  val par_json: String = encodeParToJSON[View1](par)

//  val json: JSON = f[View1](JSON(par_json))

//  println(json)

}
