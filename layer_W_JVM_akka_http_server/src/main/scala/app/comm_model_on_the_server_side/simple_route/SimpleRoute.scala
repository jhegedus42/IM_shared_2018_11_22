package app.comm_model_on_the_server_side.simple_route
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.http.scaladsl.server.Route
//  import akka.http.scaladsl.server.directives.MethodDirectives.get
//  import akka.http.scaladsl.server.directives.ParameterDirectives.parameters
//  import akka.http.scaladsl.server.directives.PathDirectives.path
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import app.model_to_be_moved_to_real_app.getViewCommunicationModel.shared.CirceUtils._
import app.model_to_be_moved_to_real_app.getViewCommunicationModel.shared.{
  GetViewHttpRouteName,
  GetViewHttpRouteProvider,
  JSONContainingGetViewPar,
  JSONContainingOptRes
}
import app.model_to_be_moved_to_real_app.getViewCommunicationModel.shared.views.View1_HolderObject.{
  View1,
  View1_Par
}
import app.model_to_be_moved_to_real_app.getViewCommunicationModel.shared.views.View2_HolderObject.View2
import app.model_to_be_moved_to_real_app.getViewCommunicationModel.shared.views.{View, View1_HolderObject}
import app.comm_model_on_the_server_side.serverSide.logic.ServerSideLogic.ServerLogicTypeClass
import io.circe.generic.auto._
import io.circe.{Decoder, Encoder, Error}
import app.server.RESTService.routes.entityCRUD.common.GetRouteBase
import app.server.stateAccess.generalQueries.InterfaceToStateAccessor
import app.shared.SomeError_Trait
import app.shared.data.model.Entity.{Data, Entity}
import app.shared.data.ref.Ref
import app.shared.data.ref.uuid.UUID
import app.shared.rest.routes.crudRequests.GetEntityRequest
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._
import io.circe.{Decoder, Encoder}

import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.ClassTag

import scala.reflect.ClassTag

case class PairOfInts(x: Int, y:Int )
case class SumOfInts(sum:Int )

// Random UUID: b503c267936147f0a52b2d47dddf93c9
// commit fc5bb550a0436ada8876f7c8a18d4b4bf9407091
// Date: Sun Jul 29 16:37:25 CEST 2018

object SumIntViewRoute_For_Testing {

  def route: Route = {
    cors() {
      post {
        path( "getSumOfIntsView" ) {
          entity( as[PairOfInts]) {
            params: PairOfInts =>
              val res: SumOfInts = SumOfInts(params.x+params.y)
              complete( res )
          }
        }
      }
    }
  }

}
