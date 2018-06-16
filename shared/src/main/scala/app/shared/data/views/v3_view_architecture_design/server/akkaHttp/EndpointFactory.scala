package app.shared.data.views.v3_view_architecture_design.server.akkaHttp

import app.shared.data.views.v3_view_architecture_design.server.stateAccess.viewQueries.ServerSideFun
import app.shared.data.views.v3_view_architecture_design.shared.JSON_ToFrom_ReqResp_Convert
import app.shared.data.views.v3_view_architecture_design.shared.views.{HttpEndpointName, HttpEndpointNameCreatorForViews}
import app.shared.data.views.v3_view_architecture_design.shared.views.common.{View, ViewReqParams, ViewResponse}
import io.circe.{Decoder, Error}

import scala.reflect.ClassTag
import io.circe.Encoder
import io.circe.Encoder
import io.circe.Encoder
import io.circe.generic.auto._
import io.circe.{Decoder, Encoder}
import io.circe.Encoder
import io.circe._
import io.circe.generic.semiauto._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

/**
  * Created by joco on 02/06/2018.
  */


object EndpointFactory {

  // we take a view and create an endpoint for it

  // here we will model that endpoint as an object that "handles" http requests

  // that is

  // 0) prints out the path of the endpoint - DONE
  // 1) it parses a JSON (that we are passing to it - that arrived over the "wire") - DONE
  // 2) turns it into a Req object - DONE

  // 3) calls the appropriate view response calculator as defined in a type class instance in stateAccess
  // 4) encodes the result into a JSON
  // 5) sends it "over the wire", i.e. returns it to the main simulator thread
  //

}



case class ServerError(description: String )



case class EndpointSimulation[V <: View: ClassTag:ServerSideFun, RLocal <: ViewReqParams[V]: Decoder,ResLocal<:ViewResponse[V]]() {

  def getEndpointPath(): HttpEndpointName = {

    val ep: HttpEndpointName = HttpEndpointNameCreatorForViews.getViewHttpEndpointName[V]()
    ep
    // call to path name creator
  }

  def f(rLocal: RLocal):ResLocal= {
//    val r:ResLocal = implicitly[ServerSideFun[V]].f(rLocal)
    ???
  }



  def processJSON(json: String )(implicit decoder: Decoder[RLocal] ): Either[ServerError, String] = {
    val parsed_json: Either[Error, RLocal] =
      JSON_ToFrom_ReqResp_Convert.decodeJSONToRequest[V, RLocal]( json )( decoder )

    val r: Either[Error, ResLocal] =
      parsed_json.right.map(
        (rl: RLocal) => { ??? }
      )

    ???

  }
}
