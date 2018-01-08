package app.server.RESTService.take3.routes.concrete

import akka.http.scaladsl.server.Route
import app.server.RESTService.take3.routes.RouteBase
import app.shared.data.model.Entity.{Data, Entity}
import app.shared.rest.routes_take3.crudCommands.CreateEntityCommCommand
import io.circe.{Decoder, Encoder}
import io.circe.generic.auto._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._

import scala.concurrent.{ExecutionContext, Future}
import akka.http.scaladsl.server.Directives._
import app.server.stateAccess.generalQueries.InterfaceToStateAccessor

import scala.reflect.ClassTag

case class CreateEntityRoute[E <: Entity](
  )(
    implicit
    i:  ClassTag[E],
    en: Encoder[E],
    de: Decoder[E],
    sa: InterfaceToStateAccessor,
    ec: ExecutionContext)
    extends RouteBase[E] {

  override val command: CreateEntityCommCommand[E] = CreateEntityCommCommand[E]()

  override def processCommand(f: command.Params ): Future[command.Result] =
    sa.createEntity( f )

  override def route: Route = postOrPutRoute(post)

}
