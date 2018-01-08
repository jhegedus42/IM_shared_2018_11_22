package app.server.RESTService.routes.entitySpecificRoutes

import akka.http.scaladsl.server.Route
import app.server.RESTService.RESTService
import app.server.RESTService.mocks.TestServerFactory
import app.server.RESTService.routes.RoutesTestBase
import app.server.RESTService.routes.generalCRUD.{GetAllEntityRouteTest, GetEntityRouteTest}
import app.server.State
import app.shared.model.ref.RefVal
import app.shared.model.entities.UserLineList
import app.shared.rest.routes_take3.crudCommands.GetAllEntitiesCommand
import app.shared.rest.routes_take3.entitySpecificCommands.GetUserLineListsCommand
import app.testHelpersServer.state.TestData
import app.testHelpersShared.data.{TestDataLabels, TestEntities, TestEntitiesForStateThree}

import scala.collection.immutable.Seq
import scalaz.Alpha.E

/**
  * Created by joco on 14/12/2017.
  */
trait GetUserLineListRouteTest {
  this: RoutesTestBase =>

  "get user line lists route" should {
    "work just fine" in {
      import io.circe.generic.auto._

      val s: RESTService = server( TestData.getTestDataFromLabels( TestDataLabels.LabelThree ) )
//      val entities: Seq[RefVal[LineText]] = getAllEntitiesHelper( s, EntityType.make[LineText] )

      val resBe:  Seq[RefVal[UserLineList]] = List(TestEntitiesForStateThree.listRV)

      val resNotBe: Seq[RefVal[UserLineList]] =  List( )

      // assert that we got all lines
      // 5587d5c97cc1457d8b629962b5ed30c4

      val r:   Route  = s.route
      val com = GetUserLineListsCommand()
      val url: String = com.queryURL(TestEntitiesForStateThree.userRef)
      type Res = com.Result

      println("url:"+url)

      val entities: List[RefVal[UserLineList]] = Get(url) ~> r ~> check {
        import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
        import io.circe.generic.auto._
        val res = responseAs[Res].toEither.right.get
        println( "response get user line list: " + res )
        res
      }

      //      println( r )
      entities shouldBe resBe
      entities should not be resNotBe

      s.shutdownActorSystem()


    }
  }

}

class GetUserLineListRoute_TestClass extends RoutesTestBase with GetUserLineListRouteTest{

  override def server(initState: State ): RESTService =
    TestServerFactory.getTestServer( initState )
}
