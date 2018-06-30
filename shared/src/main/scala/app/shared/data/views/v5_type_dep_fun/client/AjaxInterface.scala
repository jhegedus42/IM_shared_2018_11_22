package app.shared.data.views.v5_type_dep_fun.client

import app.shared.data.views.v5_type_dep_fun.server.httpService.{HttpServer, JSON}
import app.shared.data.views.v5_type_dep_fun.shared.{CirceUtils, GetViewHttpRouteName}
import app.shared.data.views.v5_type_dep_fun.shared.views.View
import io.circe.{Decoder, Encoder}

import scala.concurrent.Future
import scala.reflect.ClassTag
import scala.concurrent.ExecutionContext.Implicits.global

case class AjaxInterface(server:HttpServer){

  def getView[V<:View:ClassTag:Encoder]
      (param:V#Par)
      ( implicit  e: Encoder[V#Par] ,
                  d: Decoder[V#Res]
      ):Future[Option[V#Res]] =
  {
    val routeName= GetViewHttpRouteName.getViewHttpRouteName[V]()

    val json_request_payload : JSON=
      CirceUtils.encodeParToJSON[V](param)

    val waitingTimeInMiliSec : Double =1e6
    // return a Future which will be completed after 5 seconds
    Future
    {
      val res: Option[JSON] =
        server.serveRequests(routeName,
                             requestPayload = json_request_payload)
      wait(waitingTimeInMiliSec.toLong)
      val r2: Option[V#Res] =
        res.flatMap(r=> CirceUtils.decodeJSONToRes[V](r).right.toOption)
      r2
    }
  }

}

