package app.comm_model_on_the_server_side.serverSide.akkaHttpWebServer

import app.model_to_be_moved_to_real_app.getViewCommunicationModel.shared.CirceUtils._
import app.model_to_be_moved_to_real_app.getViewCommunicationModel.shared.{GetViewHttpRouteName, GetViewHttpRouteProvider, JSONContainingGetViewPar, JSONContainingOptRes}
import app.model_to_be_moved_to_real_app.getViewCommunicationModel.shared.views.View1_HolderObject.{View1, View1_Par}
import app.model_to_be_moved_to_real_app.getViewCommunicationModel.shared.views.View2_HolderObject.View2
import app.model_to_be_moved_to_real_app.getViewCommunicationModel.shared.views.{View, View1_HolderObject}
import app.comm_model_on_the_server_side.serverSide.logic.ServerSideLogic.ServerLogicTypeClass
import io.circe.generic.auto._
import io.circe.{Decoder, Encoder, Error}

import scala.reflect.ClassTag

case class JSON(string: String )



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
//    println("HttpServerOnTheInternet.serveRequest is called with endpoint name: " +
//           getViewHttpRouteName)

    val res: Option[JSONContainingOptRes] = getViewHttpRouteName.name match
    {
      case view1_routeName.name => {
//        println("route for view1 is called")
        Some(view1_getViewRequestHandler.
             decodeJSON2Par_SendParToLogic_EncodeResultToJSON[View1](requestPayload))
      }

      case view2_routeName.name =>
        Some(view1_getViewRequestHandler.
             decodeJSON2Par_SendParToLogic_EncodeResultToJSON[View2](requestPayload))

      case _ => None
    }
//    println("the server returns: "+res)
    res
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

//    println("GetViewRequestHandler's decodeJSON2Par_SendParToLogic_EncodeResultToJSON is called " +
//            "for the " + getGetViewHttpRouteName() + " route, with parameter: "+paramJSON)

    val r:   Either[Error, V#Par] = decodeJSONToPar[V](paramJSON)

    val parOpt: Option[V#Par]               = r.right.toOption

    val resOpt: Option[V#Res] = for {
       par<-parOpt
       res<-serverLogic.getView(par )
    } yield res

//    println("it's results is (before encoding it):"+resOpt)

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