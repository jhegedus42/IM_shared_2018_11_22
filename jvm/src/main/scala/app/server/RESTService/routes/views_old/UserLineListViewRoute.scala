package app.server.RESTService.routes.views_old

import app.server.RESTService.routes.entityCRUD.common.GetRouteBase
import app.server.stateAccess.generalQueries.InterfaceToStateAccessor
import app.shared.data.model.UserLineList
import app.shared.data.ref.{Ref, RefVal}
import app.shared.rest.routes_take3.viewCommands.UserLineListsViewCommand
import io.circe.Encoder

import scala.concurrent.{ExecutionContext, Future}
import scalaz.Scalaz._
import scalaz._

///!!! dont forget to import this crap below, to similar routes...
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._ // dont forget to import this in subclasses

/**
  * Created by joco on 04/01/2018.
  */

//
//case class UserLineListViewRoute(
//  )(
//                                 implicit
//                                 en: Encoder[UserLineListsViewCommand.gullc.Result],
//                                 sa: InterfaceToStateAccessor,
//                                 ec: ExecutionContext)
//    extends GetRouteBase[UserLineList] {
//  override val command: UserLineListsViewCommand = UserLineListsViewCommand.gullc
//
////  def g(u1:Ref[User]) = (u:Ref[User])  => u.uuid==u1.uuid
//
//
////  def g2(u1:Ref[User]) = (u:Ref[User])  => u === u1
//
//  val (a,b)= (1,2)
//
//  val c:Int =2
//
//  override def processCommand(f: command.Params ): Future[command.Result] = {
//
//    def g(l: List[RefVal[UserLineList]])  =
//      UserLineListsView(l.filter((x: RefVal[UserLineList]) => x.v.user === f))
//
//    for {
//      r <- sa.getAllEntitiesOfGivenType[UserLineList]
//      f2 = for {
//        r1 <- r
//      //        r2= r1.filter((x: RefVal[UserLineList]) => x.v.user===f )
//      } yield (g(r1))
//    } yield (f2)
//    ???
//  }
//
//
////  {
////    val u: Future[Disjunction[SomeError_Trait, RefVal[User]]] = sa.getEntity(f)
////    val g=  (y:RefVal[User]) => y.v.lineLists
////
////    val r: Future[Disjunction[SomeError_Trait, List[RefVal[UserLineList]]]] = u.map(x=> x.map(g))
////    r
////  }
//
//  import akka.http.scaladsl.server.Route
//  import akka.http.scaladsl.server.directives.MethodDirectives.get
//  import akka.http.scaladsl.server.directives.ParameterDirectives.parameters
//  import akka.http.scaladsl.server.directives.PathDirectives.path
//  import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
//
//  override def route: Route =
//    cors() {
//      get {
//        path( command.getServerPath ) {
//          parameters( 'id ) {
//            userID: String =>
//              completeRoute( processCommand _ )( Ref.makeWithUUID(userID) )
//          }
//
//        }
//      }
//    }
//}
