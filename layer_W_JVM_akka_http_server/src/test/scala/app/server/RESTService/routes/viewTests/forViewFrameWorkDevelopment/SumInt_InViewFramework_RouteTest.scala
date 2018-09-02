package app.server.RESTService.routes.viewTests.forViewFrameWorkDevelopment

import akka.http.scaladsl.model.HttpRequest
import app.comm_model_on_the_server_side.simple_route.{PairOfInts, SumIntViewRoute_For_Testing, SumOfInts}
import app.server.RESTService.AppRoutesHandler
import app.server.RESTService.mocks.TestServerFactory
import app.server.RESTService.routes.RoutesTestBase
import app.server.persistence.ApplicationState
import app.shared.data.model.LineText
import app.shared.rest.routes.crudRequests.CreateEntityRequest

/**
  */
// Random UUID: 720b43da73f04f42bda67cb50fa58078
// commit 3a7d0bc1c81a6f3d8e6aa3b6d286e8e0291af5d5
// Date: Sun Sep  2 16:40:39 EEST 2018


trait SumInt_InViewFramework_RouteTestTrait {
  this: RoutesTestBase =>

  type ResCET = CreateEntityRequest[LineText]#Result
  val cec = CreateEntityRequest[LineText]


  "simple test" should {

    "do a simple test" in {

      import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
      import io.circe.generic.auto._

      val whatWeWantToSend                 = PairOfInts(2,3)
      val URLToWhereWeWantToSendTheRequest = "/getSumOfIntsView"
      val req: HttpRequest = Post( URLToWhereWeWantToSendTheRequest, whatWeWantToSend )

      println( "Post request:" + req )

      val route = SumIntViewRoute_For_Testing.route


      req ~> route ~> check {


        val result: SumOfInts = responseAs[SumOfInts]

        println( "response from Post request:" + result )

        result shouldBe SumOfInts(5)


      }
//      s.system.terminate()
    }

  }


}

/**
  *
  */

class SumIntInViewFrameworkRouteTest_Instance extends RoutesTestBase with SumInt_InViewFramework_RouteTestTrait {
  // this is the stuff that defines what needs to be tested

  override def server(initState: ApplicationState ): AppRoutesHandler =
    TestServerFactory.getTestServer( initState )
}
