package app.server.RESTService.mocks

import akka.http.scaladsl.server.Directives.{complete, path, _}
import akka.http.scaladsl.server.Route
import app.server.RESTService.RESTService
import app.server.State
import app.server.persistence.persActor.Commands.SetStatePAResponse

import scala.concurrent.Future
//import app.server.persistence.persActor.Commands.SetStatePAResponse
import app.server.stateAccess.mocks.StateAccessorMock_prodPersAct
import app.shared.rest.TestURLs
import app.testHelpersShared.data.TestDataLabels.TestDataLabel

object TestServerFactory {

  def getTestServer(iState: State ): RESTService =
    new StateAccessorMock_prodPersAct with RESTService {

      // reset the state of the server - for testing
      def postResetStateRoute: Route = {
        def setState(s: TestDataLabel ): Future[Boolean] = {
          val r: Future[SetStatePAResponse] = actor.setState(s)
          r.map( _.success )
//        ???
        }

        println("postResetStateRoute")
        import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
        cors() {
          post {
                 println("postResetStateRoute inside ")
            path( TestURLs.resetState.urlServerSide ) {
              import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
              import io.circe.generic.auto._
              entity( as[TestDataLabel] ) {
                entityBody => // get payload/json/body ...
                  setState( entityBody )
                  complete( "resetted" )
              }
            }
          }
        }
        // 70a9db276fc744609d01093f0a8c46c3

        // test is here:18cdc128d643445cb95a23e1e8a83ca0
      }

//      override  val route = selfExp.route -- nyolc teszt elszall
      override lazy val rootPageHtml = IndexDotHtmlTestTemplate.txt

      override def initState: State = iState

      override def routeDef: Route = postResetStateRoute ~ super.routeDef

      override def shutDownService(): Unit = system.terminate()

//      override def getEntities[E <: Entity : ClassManifest](r: Ref[E]): Future[\/[SomeError_Trait, List[RefVal[E]]]] = ???
    }
}
