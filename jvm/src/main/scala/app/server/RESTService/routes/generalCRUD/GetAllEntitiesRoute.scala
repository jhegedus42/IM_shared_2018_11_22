package app.server.RESTService.take3.routes.concrete

import akka.http.scaladsl.server.Route
import app.server.RESTService.take3.routes.GetRouteBase
import app.server.stateAccess.generalQueries.InterfaceToStateAccessor
import app.shared.data.model.Entity.{Data, Entity}
import app.shared.rest.routes_take3.crudCommands.GetAllEntitiesCommand
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._
import io.circe.{Decoder, Encoder}

import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.ClassTag
// ====> 1.3.1.2 <==== task-completed implement this COMPLETED
case class GetAllEntitiesRoute[E <: Entity](
  )(
    implicit
    i:  ClassTag[E],
    en: Encoder[E],
    de: Decoder[E],
    sa: InterfaceToStateAccessor,
    ec: ExecutionContext
)
    extends GetRouteBase[E] {

  override val command: GetAllEntitiesCommand[E] = GetAllEntitiesCommand[E]()

  override def processCommand(f: command.Params ): Future[command.Result] =
    sa.getAllEntitiesOfGivenType[E]

  import akka.http.scaladsl.server.Route
  import akka.http.scaladsl.server.directives.MethodDirectives.get
  import akka.http.scaladsl.server.directives.PathDirectives.path
  import ch.megard.akka.http.cors.scaladsl.CorsDirectives._

  override def route: Route =
    cors() {
      get {
        path( command.getServerPath ) {
          completeRoute( processCommand _ )( () )

        }
      }
    }
}
