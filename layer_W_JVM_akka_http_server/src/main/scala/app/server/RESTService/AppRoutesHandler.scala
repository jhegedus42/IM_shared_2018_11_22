package app.server.RESTService

import akka.actor.Terminated
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import app.comm_model_on_the_server_side.simple_route.SumIntViewRoute_For_Testing
import app.server.RESTService.routes.entityCRUD.{
  CreateEntityRoute,
  GetAllEntitiesRoute,
  GetRoute,
  UpdateEntityRoute
}
import app.server.RESTService.routes.views.ViewRoute
import app.shared.data.model.LineWithQue
import app.shared.rest.views.viewsForDevelopingTheViewFramework.SumIntView_HolderObject.SumIntView
//import app.server.RESTService.routes.views.UserLineListViewRoute
import app.server.stateAccess.generalQueries.InterfaceToStateAccessor
import app.shared.config.Config
import app.shared.data.model.Entity.{Data, Entity}
import app.shared.data.model.{LineText, User, UserLineList}

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.reflect.ClassTag

//import shapeless.Typeable

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

/**
  * Ez mit csinál ?
  * Lekeírja, hogy
  *   - hogyan kell lekezelni az adott route-okhoz érkező kéréseket és
  *   -
  */
trait AppRoutesHandler {
  self: InterfaceToStateAccessor =>

  import io.circe._
  import io.circe.generic.auto._

  val route: Route = routeDef

  def selfExp: AppRoutesHandler with InterfaceToStateAccessor = self

  implicit lazy val system: ActorSystem = ActorSystem( "trait-Server" )

  def rootPageHtml: String

  implicit lazy val executionContext: ExecutionContextExecutor = system.dispatcher

  def shutdownActorSystem(): Future[Terminated] = system.terminate()

  implicit lazy val isa: InterfaceToStateAccessor = this

  def crudEntityRoute[E <: Entity: ClassTag: Decoder: Encoder]: Route = {
    new UpdateEntityRoute[E]().route ~
      new CreateEntityRoute[E]().route ~
      new GetAllEntitiesRoute[E].route ~
      new GetRoute[E]().route
  }

  /**
    * This defines the routes that the server can "handle".
    * b7dd04db32254f069a76c952118e9ae3$5e45b350c3d7df91abb31d34817ad48226d70ff8
    *
    * @return the routes that the server can "handle"
    */
  def routeDef: Route = {

    val routeForSumIntView =
      ViewRoute.getRouteForView[SumIntView]() // copied from d7b8aea40f454a46af529da21328f1aa

    val result: Route =
      routeForSumIntView ~
      SumIntViewRoute_For_Testing.route ~
      crudEntityRoute[LineText] ~
      crudEntityRoute[UserLineList] ~
      crudEntityRoute[LineWithQue] ~
      crudEntityRoute[User] ~
      StaticStuff.staticRootFactory( rootPageHtml )

    result
  }

  def start(args: Array[String] ): Unit = {

    implicit val materializer = ActorMaterializer()

    // needed for the future flatMap/onComplete in the end
    // val bindingFuture = Http().bindAndHandle( route, "localhost", Config.port )

    var host: String = null
    if (args.length == 0) {
      host = "localhost"
    } else {
      host = args( 0 )
    }

    val bindingFuture: Future[Http.ServerBinding] = Http().bindAndHandle( route, host, Config.port )

    // mac
    // val bindingFuture = Http().bindAndHandle( route, "192.168.2.50", Config.port )
    // server

    println( s"listening on ${host}:${Config.port}" )
  }

}
