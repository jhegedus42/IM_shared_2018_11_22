package app.server.RESTService.take3.routes.concrete

import akka.http.scaladsl.server.Route
import app.server.RESTService.take3.routes.GetRouteBase
import app.server.stateAccess.generalQueries.InterfaceToStateAccessor
import app.shared.SomeError_Trait
import app.shared.model.Entity.Entity
import app.shared.model.ref.Ref
import app.shared.model.ref.uuid.UUID
import app.shared.rest.routes_take3.crudCommands.{GetEntityCommand}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._
import io.circe.{Decoder, Encoder}

import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.ClassTag

class GetRoute[E <: Entity](
  )(
    implicit
    i:  ClassTag[E],
    en: Encoder[E],
    de: Decoder[E],
    sa: InterfaceToStateAccessor,
    ec: ExecutionContext)
    extends GetRouteBase[E] {

  override val command: GetEntityCommand[E] = GetEntityCommand[E]

  override def processCommand(f: command.Params ): Future[command.Result] = {
    import scalaz._
    //ezzel elkeruljuk a wrong type hibat

    val refDis: \/[SomeError_Trait, Ref[E]] =
      UUID
        .validate_from_String( f )
        .map( Ref.makeWithUUID[E]( _ ) )

    val fr: Future[command.Result] = refDis
      .map( sa.getEntity( _ ) )
      .fold( i => Future( (-\/( i ) ) ), i => i )
    fr
  }

  import akka.http.scaladsl.server.Route
//  import akka.http.scaladsl.server.directives.MethodDirectives.get
//  import akka.http.scaladsl.server.directives.ParameterDirectives.parameters
//  import akka.http.scaladsl.server.directives.PathDirectives.path
  import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
  override def route: Route = {

    // no / at beginning -- but on the test side there is / in the URL

    import akka.http.scaladsl.server.directives.RouteDirectives.complete

    import akka.http.scaladsl.server.Directives._

    get {
      println( "pina" )
      path( "test" ) {
        println( "pina - test" )
        complete( "fasz" )
      }
    } ~
      cors() {
        println( "Get route" )
        get {
          println( "Get route inner" )
          path( command.getServerPath ) {
            parameters( 'id ) {
              id: String =>
                completeRoute( processCommand _ )( id )

            }
          }
        }
      }

  }
}
