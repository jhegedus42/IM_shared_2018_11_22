package app.shared.data.views.v5_type_dep_fun.client

import app.shared.data.views.v5_type_dep_fun.serverSide.akkaHttpWebServer.{
  HttpServerOnTheInternet,
  JSONContainingGetViewPar
}
import app.shared.data.views.v5_type_dep_fun.shared.CirceUtils.JSONContainingOptRes
import app.shared.data.views.v5_type_dep_fun.shared.{
  CirceUtils,
  GetViewHttpRouteName,
  GetViewHttpRouteProvider
}
import app.shared.data.views.v5_type_dep_fun.shared.views.View
import io.circe.{Decoder, Encoder}

import scala.concurrent.Future
import scala.reflect.ClassTag
import scala.concurrent.ExecutionContext.Implicits.global

object JSAjaxAPI {

  lazy val server = HttpServerOnTheInternet()

  def postRequest(
      json:      JSONContainingGetViewPar,
      routeName: GetViewHttpRouteName
    ): Future[Option[JSONContainingOptRes]] = {
    Future {
      val waitingTimeInMiliSec = 5 * 1000 // + something random ? or some state ?
      wait( waitingTimeInMiliSec.toLong )
      val res: Option[JSONContainingOptRes] = server.serveRequest( routeName, json )
      res
    }
  }
}

case class GetViewAjaxRequest[V <: View](par: V#Par, ajaxResFuture: Future[Option[V#Res]] )

case class AjaxInterface(server: HttpServerOnTheInternet ) {

  def getAJAXGetViewRequest[V <: View: ClassTag: Encoder](
      param: V#Par
    )(
      implicit
      e: Encoder[V#Par],
      d: Decoder[V#Res]
    ): GetViewAjaxRequest[V] = {
    val routeName: GetViewHttpRouteName = GetViewHttpRouteProvider.getGetViewHttpRouteName[V]()

    val json_request_payload: JSONContainingGetViewPar =
      CirceUtils.encodeParToJSON[V]( param )

    val resFuture: Future[Option[JSONContainingOptRes]] =
      JSAjaxAPI.postRequest( json_request_payload, routeName )

    val futureOptionVResReturnValue: Future[Option[V#Res]] = for { // for the Future
      arrivedOptionJSONContainingRes <- resFuture
      // what happens to a Future if you flatmap it and before that the future completes...
      // will the onComplete method of the flatmapp-ed future still be called ?

      arrivedOptionVRes: Option[V#Res] = arrivedOptionJSONContainingRes.flatMap(
        (r: JSONContainingOptRes) => CirceUtils.decodeJSONContainingOptResToOptRes[V]( r ).right.toOption
                                                                               )
    } yield (arrivedOptionVRes)
    GetViewAjaxRequest(param, futureOptionVResReturnValue)
  }

}