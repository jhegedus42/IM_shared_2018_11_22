package app.server.RESTService

import akka.actor.Terminated
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import app.server.RESTService.routes.entitySpecific.get.GetUserLineListRoute
import app.server.RESTService.take3.routes.concrete.{CreateEntityRoute, GetAllEntitiesRoute, GetRoute, UpdateEntityRoute}
import app.server.stateAccess.generalQueries.InterfaceToStateAccessor
import app.shared.config.Config
import app.shared.data.model.Entity.Data
import app.shared.data.model.{LineText, User, UserLineList}
import app.shared.data.model.UserLineList.LineListElement

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.reflect.ClassTag


//import shapeless.Typeable


import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer



trait RESTService {
  self: InterfaceToStateAccessor =>

  import io.circe._
  import io.circe.generic.auto._

  val route: Route = routeDef

  def selfExp: RESTService with InterfaceToStateAccessor = self

  implicit lazy val system: ActorSystem = ActorSystem( "trait-Server" )

  // todolater figure out what happens if multiple update requests are
  // made from the client and they arrive out of order,
  // maybe including some time stamp would be useful ?
  // or some kind of version number ...
  def rootPageHtml: String

  implicit lazy val executionContext: ExecutionContextExecutor = system.dispatcher

  def shutdownActorSystem(): Future[Terminated] = system.terminate()

  implicit lazy val isa: InterfaceToStateAccessor = this

  def crudEntityRoute[E <: Data: ClassTag: Decoder: Encoder]: Route = {
    new UpdateEntityRoute[E]().route ~
    new CreateEntityRoute[E]().route ~
    new GetAllEntitiesRoute[E].route ~
    new GetRoute[E]().route
  }



  def routeDef: Route =
    crudEntityRoute[LineText] ~
    crudEntityRoute[UserLineList] ~
    crudEntityRoute[LineListElement] ~
    new GetUserLineListRoute().route ~
    crudEntityRoute[User] ~
      StaticStuff.staticRootFactory( rootPageHtml )

  // createLineForUser

//  def updateEntityRoute[E <: Entity, R <: IMRouteName](
//      r: R
//    )(
//      implicit
//      i:  ClassTag[E],
//      en: Encoder[E],
//      dc: Decoder[E]
//    ): Route = {
//
//    def completeUpdateEntityRouteJSON[Ent <: Entity](
//        refVal: RefVal[Ent]
//      )(
//        implicit
//        i:  ClassTag[Ent],
//        en: Encoder[Ent]
//      ): Route = {
//
//      assert(
//        refVal.r.entityType.type_as_string == i.runtimeClass.getSimpleName
//      )
//      //ezzel elkeruljuk a wrong type hibat
//      // 70fa52d29a254b89bda07c0c52c9f68a commit 2ec8ad4ba0e9a407bff4a58217d78f3b774cbfe3 Fri Nov  3 19:40:06 EET 2017
//
//      import app.shared.rest.HttpResultTypes.HttpUpdateRequestResult
//
//      val r: Future[HttpUpdateRequestResult[Ent]] =
//        updateEntity( refVal )
//          .map( HttpUpdateRequestResult( _ ) )
//
//      val fr: Future[ToResponseMarshallable] =
//        r //.map(ToMarschallableImplicits.c2)
//          .map( ToResponseMarshallable.apply( _ ) )
//
//      complete( fr )
//    }
//
//    import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
//
//    val url_path: String = RouteToURL.routeToURL( r )
//
//    val defaultMethods: Seq[HttpMethod] =
//      CorsSettings.defaultSettings.copy( allowGenericHttpRequests = false ).allowedMethods
//    import akka.http.scaladsl.model.HttpMethods.PUT
//    val settings = CorsSettings.defaultSettings
//      .copy( allowedMethods = List( PUT ) )
//
//    // szeretnek ide vmi type safe url-t es ez ne erje el a klienshez valo dolgokat
//
//    cors( settings ) {
//      //enabling cors
//      // todolater make this configurable ... in a type safe way...
//      // by using self type annotations , asking for config traits
//      // for example trait ... ConfigAkkaHttpServer --> WTF is this ? ? ? (reading it later)
//      println( "looking for put" )
//      put {
//        path( url_path.substring( 1 ) ) {
//          println( s"we match $url_path" )
//          println( "we match put" )
//
//          import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
//          entity( as[RefVal[E]] ) {
//
//            // connect to this
//            //b367c298bb35479a81cca6efc51e112f commit 2ec8ad4ba0e9a407bff4a58217d78f3b774cbfe3 Fri Nov  3 19:34:33 EET 2017
//
//            // but maybe this is already taken care of here
//            // ( 70fa52d29a254b89bda07c0c52c9f68a
//            // commit 2ec8ad4ba0e9a407bff4a58217d78f3b774cbfe3
//            // Fri Nov  3 19:40:06 EET 2017 )
//
//            // some comment on using type safe decode
//            //8d01415879e74b38ac5be3499fb61d02 commit 2ec8ad4ba0e9a407bff4a58217d78f3b774cbfe3 Fri Nov  3 19:38:13 EET 2017
//
//            refValBody => // get payload/json/body ...
//              completeUpdateEntityRouteJSON[E]( refValBody )
//          }
//        }
//      }
//    }
//
//  }


  def start(args: Array[String] ): Unit = {

    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
//    val bindingFuture = Http().bindAndHandle( route, "localhost", Config.port )
    val host=args(0)

    val bindingFuture = Http().bindAndHandle( route, host, Config.port )
    //mac
//    val bindingFuture = Http().bindAndHandle( route, "192.168.2.50", Config.port )
    //server
    println( s"listening on ${host}:${Config.port}" )
  }

  //  def getAllLines() = {
  //    val name = "getAllLines"
  //    get {
  //      println("we match get")
  //      path(name) { // no / at beginning -- but on the test side there is / in the URL
  //        println(s"we match $name")
  //        parameters('id) { id =>
  //          completeGetRouteJSON[E](id, EntityType(name))
  //        }
  //      }
  //    }
  //
  //  }
  //7efd892c22f94d3bac08dec027be0872

}
