package app.server.RESTService.routes

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import app.server.RESTService.RESTService
import app.server.RESTService.mocks.TestServerFactory
import app.server.RESTService.routes.generalCRUD.{GetAllEntityRouteTest, GetEntityRouteTest}
import app.server.State
import app.shared.data.model.Entity.Data
import app.shared.data.ref.RefVal
import app.shared.data.model.{DataType, LineText}
import app.shared.rest.routes.crudCommands.{GetAllEntitiesCommand, GetEntityCommand}
import app.testHelpersServer.state.TestData
import io.circe.Decoder
import org.scalatest.{Assertion, Matchers, WordSpec}

import scala.collection.immutable.Seq
import scala.reflect.ClassTag

/**
  * these are things that are used by all tests that test the routes
  * making things more dry
  * this is a common denominator for all route tests
  */
trait RoutesTestBase extends WordSpec with Matchers with ScalatestRouteTest {

  def server(initState: State ): RESTService

  type ResInBase = GetEntityCommand[LineText]#Result

  def testGetEntityHelper(url: String, assert: ResInBase => Unit ): Unit = {

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

  /**
    *
    * @param restService
    * @param functionToRun
    * @tparam T
    * @return
    */
  def runWithServer[T](restService: RESTService )(functionToRun: RESTService => T ): T = {
    val res: T = functionToRun(restService)
    restService.system.terminate()
    res
  }

  def getAllEntitiesHelper[E <: Data: ClassTag: Decoder: GetAllEntitiesCommand](
      server:     RESTService,
      entityType: DataType
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

/**
  * This defines what tests to run.
  * This is actually the "test class" which is looked at by the test runner.
  */
class RoutesTest_PersActor_Class extends RoutesTestBase with GetEntityRouteTest with GetAllEntityRouteTest {

  override def server(initState: State ): RESTService =
    TestServerFactory.getTestServer( initState )
}
