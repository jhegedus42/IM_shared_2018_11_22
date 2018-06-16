package app.shared.data.views.v4_path_dep_type_based_view_architecture.server.http

import app.shared.data.views.v4_path_dep_type_based_view_architecture.shared.ArchitectureModel._
import app.shared.data.views.v4_path_dep_type_based_view_architecture.shared.{EndpointName, JSON}
import io.circe.Decoder
import io.circe.generic.auto._

import scala.reflect.ClassTag

/**
  * Created by joco on 16/06/2018.
  */



abstract class Endpoint[V<:View,Rq,Rsp] {

  val e: EndpointName = getEndpointName()

  def processJSON(json: JSON) : Option[JSON]


}

object EndpointCreator{

  // this makes sure that only "valid endpoints are created", that is, View determines the
  // input and output parameters
  // this is the only place where endpoints are generated
  def getEndpoint[V<:View,Rq:Decoder,Rsp]
        ()
        (implicit classTag  : ClassTag[V], aux : ViewParams.Aux[V,Rq,Rsp] ):
           Endpoint[V,Rq,Rsp] =
    {
      def g(json: JSON)  : Option[JSON] ={

        // 1) to which when we pass a String it parses it to ReqParams and then


        // 2) calls the appropriate server side f, which returns Response then


        // 3) encodes Response into JSON


        val res:Option[JSON] = ???
        res
      }
        ???
    }


  import io.circe._
  import io.circe.parser._

  def decodeJSONToRequest[V<:View,Rq:Decoder,Rsp]
        (json: JSON)
        (implicit aux:ViewParams.Aux[V,Rq,Rsp] ) :
          Either[Error,Rq] =
      {
        decode(json.asString)
      }

  // let's create a function endpointCreator,
  // that creates a class that has a h() that uses g()
  // from below



  //  println(getEndpointName[View1.type]())

  // val impl = ServerSideFunction.View1ServerSideImplicit
  //  implicit val v= ArchitectureModel.View1

}

object TestServerSide extends App{

  EndpointCreator.
  decodeJSONToRequest[
    View1,
    View1_ViewParams_Instance.Req,
    View1_ViewParams_Instance.Resp](JSON("bla"))

}

