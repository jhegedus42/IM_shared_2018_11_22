package app.server.RESTService.routes

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import app.server.RESTService.RESTService
import app.server.RESTService.mocks.TestServerFactory
import app.server.RESTService.routes.generalCRUD.{GetAllEntityRouteTest, GetEntityRouteTest}
import app.server.State
import app.shared.data.model.Entity.Entity
import app.shared.data.ref.RefVal
import app.shared.data.model.{EntityType, LineText}
import app.shared.rest.routes_take3.crudCommands.{GetAllEntitiesCommand, GetEntityCommand}
import app.testHelpersServer.state.TestData
import io.circe.Decoder
import org.scalatest.{Assertion, Matchers, WordSpec}

import scala.collection.immutable.Seq
import scala.reflect.ClassTag

trait RoutesTestBase extends WordSpec with Matchers with ScalatestRouteTest {

  def server(initState: State ): RESTService

  type ResInBase = GetEntityCommand[LineText]#Result

  def getEntityHelper(url: String, assert: ResInBase => Unit ): Unit = {

    val s: RESTService = server( TestData.TestState_LabelOne_OneLine_WithVersionZero_nothing_else )
    val r: Route       = s.route
    Get( url ) ~> r ~> check {

      import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
      import io.circe.generic.auto._
      // ^^^ ez kell ide hogy a valaszt tudjuk dekodolni a kovetkezo sorban
      val r: ResInBase = responseAs[ResInBase]
      assert( r )
      println( r )

    }
    s.shutdownActorSystem()
  }

  def assert_RefVal_for_LineText_is_present(
      s:      RESTService,
      refVal: RefVal[LineText],
      xor:    Boolean
    ): Assertion = {

    val r: Route = s.route

    val url: String = GetEntityCommand[LineText]().queryURL( refVal.r )

    Get( url ) ~> r ~> check {

      import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
      import io.circe.generic.auto._
      // ^^^ ez kell ide hogy a valaszt tudjuk dekodolni a kovetkezo sorban

      type Res = GetEntityCommand[LineText]#Result
      val res: Res =
        responseAs[Res] //dd25434d2997499aa3a984a2af991ffe
      println( "---- assert LineText is present start ----" )
      println( "response " + res )
      println( "refVal : " + refVal )
      println( "should be equal ? "+xor)
      println( "---- assert LineText is present end ----" )
      //                             1 shouldNot be 2
      assert( !xor ^ (res.toEither.right.get == refVal) )
    }
  }

  def runWithServer[T](s: RESTService )(f: RESTService => T ): T = {
    val res: T = f( s )
    s.system.terminate()
    res
  }

  def getAllEntitiesHelper[E <: Entity: ClassTag: Decoder: GetAllEntitiesCommand](
      server:     RESTService,
      entityType: EntityType
    ): Seq[RefVal[E]] = {

    val r: Route = server.route
    val gAEs  = implicitly[GetAllEntitiesCommand[E]]
    val gAEsL = GetAllEntitiesCommand[E]()
    type Res = gAEsL.Result

    val url: String = gAEs.queryURL

    val res2=Get( url ) ~> r ~> check {
      import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
      import io.circe.generic.auto._
      val res=responseAs[Res].toEither.right.get
      println("response getAllEntHelper: "+res)
      res
    }


    println("outer response "+res2)
    res2


  }

}

class RoutesTest_PersActor_Class extends RoutesTestBase with GetEntityRouteTest with GetAllEntityRouteTest {

  override def server(initState: State ): RESTService =
    TestServerFactory.getTestServer( initState )
}
