package app.server.RESTService.routes.entitySpecific.get

import app.server.RESTService.take3.routes.GetRouteBase
import app.server.stateAccess.generalQueries.InterfaceToStateAccessor
import app.shared.SomeError_Trait
import app.shared.model.entities.UserLineList
import app.shared.model.ref.{Ref, RefVal}
import app.shared.rest.routes_take3.entitySpecificCommands.GetUserLineListsCommand
import io.circe.Encoder

import scala.concurrent.{ExecutionContext, Future}
import scalaz.\/
import scalaz._
import Scalaz._

///!!! dont forget to import this crap below, to similar routes...
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._ // dont forget to import this in subclasses

/**
  * Created by joco on 04/01/2018.
  */
case class GetUserLineListRoute(
  )(
    implicit
    en: Encoder[GetUserLineListsCommand.gullc.Result],
    sa: InterfaceToStateAccessor,
    ec: ExecutionContext)
    extends GetRouteBase[UserLineList] {
  override val command: GetUserLineListsCommand = GetUserLineListsCommand.gullc

//  def g(u1:Ref[User]) = (u:Ref[User])  => u.uuid==u1.uuid


//  def g2(u1:Ref[User]) = (u:Ref[User])  => u === u1

  val (a,b)= (1,2)

  val c:Int =2

  override def processCommand(f: command.Params ): Future[command.Result] =
    for {
      r <- sa.getAllEntitiesOfGivenType[UserLineList]
      f2= for {
        r1  <- r
        r2= r1.filter((x: RefVal[UserLineList]) => x.v.user===f )
      //        r2= r1.filter((x: RefVal[UserLineList]) => x.v.user===f )
      } yield (r2)
    } yield (f2)
//  {
//    val u: Future[Disjunction[SomeError_Trait, RefVal[User]]] = sa.getEntity(f)
//    val g=  (y:RefVal[User]) => y.v.lineLists
//
//    val r: Future[Disjunction[SomeError_Trait, List[RefVal[UserLineList]]]] = u.map(x=> x.map(g))
//    r
//  }

  import akka.http.scaladsl.server.Route
  import akka.http.scaladsl.server.directives.MethodDirectives.get
  import akka.http.scaladsl.server.directives.ParameterDirectives.parameters
  import akka.http.scaladsl.server.directives.PathDirectives.path
  import ch.megard.akka.http.cors.scaladsl.CorsDirectives._

  override def route: Route =
    cors() {
      get {
        path( command.getServerPath ) {
          parameters( 'id ) {
            userID: String =>
              completeRoute( processCommand _ )( Ref.makeWithUUID(userID) )
          }

        }
      }
    }
}
