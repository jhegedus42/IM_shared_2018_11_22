package app.server.RESTService.routes.generalCRUD

import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.server.Route
import app.server.RESTService.RESTService
import app.server.RESTService.mocks.TestServerFactory
import app.server.RESTService.routes.RoutesTestBase
import app.server.State
import app.server.stateAccess.generalQueries.InterfaceToStateAccessor
import app.shared.data.ref.RefVal
import app.shared.data.model.{DataType, LineText}
import app.shared.rest.routes.crudCommands.CreateEntityCommCommand
import app.testHelpersServer.state.TestData
import app.testHelpersShared.data.TestEntities

import scala.collection.immutable.Seq
import scala.concurrent.Await

trait CreateEntityTest {
  this: RoutesTestBase =>

  type ResCET = CreateEntityCommCommand[LineText]#Result
  val cec= CreateEntityCommCommand[LineText]


  def createLine(
      s:            RESTService,
      lineToBeSent: LineText,
      assertion:    ResCET => Unit
    ): ResCET = {

    import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
    import io.circe.generic.auto._

    // ^^^ ez kell ide hogy a valaszt tudjuk dekodolni a kovetkezo sorban
    //    val url: String =
    //      CreateEntityURL( EntityType.make[LineText] ).clientPathWithSlashWithoutHost.asString

    val url: String      = CreateEntityCommCommand[LineText]().queryURL()
    val r:   Route       = s.route
    val req: HttpRequest = Post( url, lineToBeSent )

    println( "Url:" + url )
    println( "Post request:" + req )

    val res: ResCET = req ~> r ~> check {

      // ^^^ ez kell ide hogy a valaszt tudjuk dekodolni a kovetkezo sorban
      val result: ResCET = responseAs[ResCET]

      println( "response from Post request:" + result )
      assertion( result )
      result
    }
    res
  }

  "create entity route" should {
    "create line - happy path" in {
      val s: RESTService =
        server( TestData.TestState_LabelOne_OneLine_WithVersionZero_nothing_else )

      import scala.concurrent.duration._
      val line = LineText( title =  "macska" ,text="test" )

      val mock: RESTService with InterfaceToStateAccessor = s.selfExp
      val r1:   Boolean                                   = Await.result( mock.doesEntityExist( line ), 2 seconds )
      assert( !r1 )

      import io.circe.generic.auto._


      //get list of lines - before
      val entitiesBefore: Seq[RefVal[LineText]] = getAllEntitiesHelper( s, DataType.make[LineText])

      // create line here
      val res: ResCET = createLine(s, line, {
        req =>
          // mit ad ez vissza ??
          req.toEither.right.get.v shouldBe line
          ()
      })

      import monocle.macros.syntax.lens._
      val rv: RefVal[LineText] = res.toEither.right.get

      assert_RefVal_for_LineText_is_present( s, rv, true )
      // letrehozott line tenyleg benne van

      assert_RefVal_for_LineText_is_present( s, rv.lens( _.v.title ).set( "fasz"  ), false )
      // a fasz verzio nincs benne

      import io.circe.generic.auto._

      //get list of lines - after
      val entitiesAfter: Seq[RefVal[LineText]] = getAllEntitiesHelper( s, DataType.make[LineText])

      println("after:"+entitiesAfter)

      val before: Set[RefVal[LineText]] = Set( TestEntities.refValOfLineV0 )
      val after:  Set[RefVal[LineText]] = Set( TestEntities.refValOfLineV0, rv )

      val after1= entitiesAfter.toSet
      after1 shouldBe after



      entitiesAfter.toSet should not be before

      entitiesBefore.toSet shouldBe before
      entitiesBefore.toSet should not be after

      s.system.terminate()
    }

  }

}


class CreateEntityRouteTest extends
  RoutesTestBase with
  CreateEntityTest{ // this is the stuff that defines what needs to be tested

  override def server(initState: State ): RESTService =
    TestServerFactory.getTestServer( initState )
}
