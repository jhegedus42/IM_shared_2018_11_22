package app.shared.data.views.v4_path_dep_type_based_view_architecture.server.http

import app.shared.data.views.v3_view_architecture_design.server.stateAccess.viewQueries.ServerSideFun
import app.shared.data.views.v4_path_dep_type_based_view_architecture.server.logic.ServerSideFunction
import app.shared.data.views.v4_path_dep_type_based_view_architecture.shared.ArchitectureModel._
import app.shared.data.views.v4_path_dep_type_based_view_architecture.shared.{EndpointName, JSON}
import io.circe.{Decoder, Error}
import io.circe.generic.auto._

import scala.reflect.ClassTag

abstract class Endpoint[V <: View, Rq, Rsp] {

  val e: EndpointName = getEndpointName()

  def processJSON(json: JSON ): Option[JSON]

}

object EndpointCreator {

  // this makes sure that only "valid endpoints are created", that is, View determines the
  // input and output parameters
  // this is the only place where endpoints are generated
  def getEndpoint[V <: View](v:ViewParams[V]
    )(
      implicit
      classTag: ClassTag[V],
      aux:      ViewParams.Aux[V, v.Req, v.Resp],
//      serverLogic : ServerSideFunction[V,v.Req,v.Resp],
      decoder: Decoder[v.Req]
    ): Endpoint[V, v.Req, v.Resp] = {
    def g(json: JSON ): Either[String, JSON] = {

      // 1) to which when we pass a String it parses it to ReqParams and then
      val req:  Either[Error, v.Req]  = decodeJSONToRequest( json )
      val reqs: Either[String, v.Req] = req.left.map( _.toString )

//      val ff: (serverLogic.v.Req) => serverLogic.v.Resp = serverLogic.f
//      val ff: (serverLogic.Rq) => serverLogic.Rsp = serverLogic.f


//       2) calls the appropriate server side f, which returns Response then

//      reqs.right.flatMap((req: v.Req) => {
//         val res: v.Resp = serverLogic.f(req)
//        ???
//      } )

//      serverLogic.f


      // 3) encodes Response into JSON

      ???
    }
//    new Endpoint[V, Rq, Rsp] {
//      ???
//      override def processJSON(json: JSON ): Option[JSON] = ???
//    }
    ???
  }

  import io.circe._
  import io.circe.parser._

  def decodeJSONToRequest[V <: View, Rq: Decoder, Rsp](
      json: JSON
    )(
      implicit
      aux: ViewParams.Aux[V, Rq, Rsp]
    ): Either[Error, Rq] = {
    decode( json.asString )
  }

  // let's create a function endpointCreator,
  // that creates a class that has a h() that uses g()
  // from below
  //  println(getEndpointName[View1.type]())

  // val impl = ServerSideFunction.View1ServerSideImplicit
  //  implicit val v= ArchitectureModel.View1

}

object TestServerSide extends App {

  EndpointCreator.decodeJSONToRequest[View1, View1_ViewParams_Instance.Req, View1_ViewParams_Instance.Resp](
    JSON( "bla" )
  )

}
