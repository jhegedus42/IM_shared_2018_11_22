package app.shared.data.views.v5_type_dep_fun.serverSide.akkaHttpWebServer

import app.shared.data.views.v5_type_dep_fun.serverSide.logic.ServerSideLogic.ServerLogicTypeClass
import app.shared.data.views.v5_type_dep_fun.shared.CirceUtils._
import app.shared.data.views.v5_type_dep_fun.shared.{GetViewHttpRouteName, GetViewHttpRouteProvider}
import app.shared.data.views.v5_type_dep_fun.shared.views.View1.{View1, View1_Par}
import app.shared.data.views.v5_type_dep_fun.shared.views.View2.View2
import app.shared.data.views.v5_type_dep_fun.shared.views.{View, View1}
import io.circe.generic.auto._
import io.circe.{Decoder, Encoder, Error}

import scala.reflect.ClassTag

case class JSON(string: String )

case class JSONContainingGetViewPar(string: String )

case class HttpServerOnTheInternet(){
  val view1_getViewRequestHandler: GetViewRequestHandler[View1] =
    new GetViewRequestHandler[View1]()

  val view2_getViewRequestHandler: GetViewRequestHandler[View2] =
    new GetViewRequestHandler[View2]()

  val view1_routeName: GetViewHttpRouteName =
    view1_getViewRequestHandler.getGetViewHttpRouteName()

  val view2_routeName: GetViewHttpRouteName =
    view2_getViewRequestHandler.getGetViewHttpRouteName()

  def serveRequest(getViewHttpRouteName:GetViewHttpRouteName,
                   requestPayload      :JSONContainingGetViewPar):Option[JSONContainingOptRes]={
    // case based on endpointName
    getViewHttpRouteName.name match
    {
      case view1_routeName.name =>
        Some(view1_getViewRequestHandler.
             decodeJSON2Par_SendParToLogic_EncodeResultToJSON[View1](requestPayload))

      case view2_routeName.name =>
        Some(view1_getViewRequestHandler.
             decodeJSON2Par_SendParToLogic_EncodeResultToJSON[View2](requestPayload))

      case _ => None
    }
  }



}

case class GetViewRequestHandler[V<:View:ClassTag](){
  def decodeJSON2Par_SendParToLogic_EncodeResultToJSON[V <: View]
      (
        paramJSON: JSONContainingGetViewPar
      )
      ( implicit
        decoder: Decoder[V#Par],
        encoder: Encoder[V#Res],
        serverLogic:ServerLogicTypeClass[V]
      ): JSONContainingOptRes = {

    val r:   Either[Error, V#Par] = decodeJSONToPar[V](paramJSON)

    val parOpt: Option[V#Par]               = r.right.toOption

    val resOpt: Option[V#Res] = for {
       par<-parOpt
       res<-serverLogic.getView(par )
    } yield res

    (encodeOptResToJSONContainingOptRes[V](resOpt))


  }

  def getGetViewHttpRouteName():GetViewHttpRouteName=
    GetViewHttpRouteProvider.getGetViewHttpRouteName[V]()
}

object HttpEndpointTestDriver extends App {

  //we want an endopoint builder -parameterized on View - that
  // creates a class that contains a function f, that :


//  val par: View1_Par = View1.View1_Par("pina")

//  val par_json: String = encodeParToJSON[View1](par)

//  val json: JSON = f[View1](JSON(par_json))

//  println(json)

}
