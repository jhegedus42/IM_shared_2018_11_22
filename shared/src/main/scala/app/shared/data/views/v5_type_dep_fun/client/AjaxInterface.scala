package app.shared.data.views.v5_type_dep_fun.client


import app.shared.data.ref.uuid.UUID
import app.shared.data.views.v5_type_dep_fun.serverSide.akkaHttpWebServer.{HttpServerOnTheInternet, JSONContainingGetViewPar}
import app.shared.data.views.v5_type_dep_fun.shared.CirceUtils.JSONContainingOptRes
import app.shared.data.views.v5_type_dep_fun.shared.{CirceUtils, GetViewHttpRouteName, GetViewHttpRouteProvider}
import app.shared.data.views.v5_type_dep_fun.shared.views.View
import io.circe.{Decoder, Encoder}

import scala.concurrent.Future
import scala.reflect.ClassTag
import scala.concurrent.ExecutionContext.Implicits.global

object JSAjaxAPI {

  var howManySecondsToWait = 0.0

  lazy val server = HttpServerOnTheInternet()

  def postRequest(
      json:      JSONContainingGetViewPar,
      routeName: GetViewHttpRouteName
    ): Future[Option[JSONContainingOptRes]] = {


    howManySecondsToWait = howManySecondsToWait + 5.0


    Future {
      // + something random ? or some state ?
      val waitingTimeInMiliSec = howManySecondsToWait * 1000
      println( "before waiting "+waitingTimeInMiliSec )

      Thread.sleep( waitingTimeInMiliSec.toLong )
      println( "after waiting, " +waitingTimeInMiliSec )

      val res: Option[JSONContainingOptRes] = server.serveRequest( routeName, json )
//      println( "JSAjaxAPI.postRequest result is " + res )
      res
    }

  }
}

case class GetViewAjaxRequest[V <: View](par: V#Par, ajaxResFuture: Future[Option[V#Res]]  ){
  val uUID: UUID = UUID.random()
}
//
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
//      _ <- Future( println( "resFuture has arrived, it contains: " + arrivedOptionJSONContainingRes ) )
      // what happens to a Future if you flatmap it and before that the future completes...
      // will the onComplete method of the flatmapp-ed future still be called ?

      arrivedOptionVRes: Option[V#Res] = arrivedOptionJSONContainingRes.flatMap(
        {
          (r: JSONContainingOptRes) =>
            {
              val decoded: Option[V#Res] =
              CirceUtils.decodeJSONContainingOptResToOptRes[V]( r ).right.toOption
//              println("we decoded the JSON that arrived from the internet, the decoded object is: "+decoded)
              decoded
            }
        }
      )
    } yield (arrivedOptionVRes)

//    println( "getAJAXGetViewRequest's" + futureOptionVResReturnValue )

    GetViewAjaxRequest( param, futureOptionVResReturnValue )
  }

}
