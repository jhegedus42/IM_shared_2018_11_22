package app.server.RESTService.routes

import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.server.Route
import app.server.RESTService.RESTService
import app.testHelpersShared.data.TestDataLabels.TestDataLabel

/**
  * Created by joco on 14/12/2017.
  */
trait ResetStateRoute_Test {
  this: RoutesTestBase =>

  def resetState(s: RESTService, tdl: TestDataLabel ): Unit = {
    import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
    import io.circe.generic.auto._
    // ^^^ ez kell ide hogy a valaszt tudjuk dekodolni a kovetkezo sorban
    val r:   Route       = s.route
    val req: HttpRequest = Post( "/resetState", tdl )

    println( "Put request:" + req )

    val r2: String = req ~> r ~> check {

      // ^^^ ez kell ide hogy a valaszt tudjuk dekodolni a kovetkezo sorban

      val result: String = responseAs[String] //4571dd7bb6ab4be996284283ab936514

      println( "response from Post request:" + result )
      //      import org.scalactic._
      //      import TypeCheckedTripleEquals._

      assert( result === "resetted" )
      result
    }

  }
  //  "reset route" should {
  //    "set the db state" in {
  //      object Test extends UpdateTestSetup {
  //
  //        override def fun(): Unit = {
  //
  //          checkInitState()
  //
  //          updateRefValLine( s, v0fasz, result => {
  //            result.rv.toEither.right.get shouldBe v1fasz
  //            println( "what it should be :" + v1fasz )
  //
  //          } )
  //
  //          def checkUpdatedState(): Assertion = {
  //
  //            assert_RefVal_for_LineText_is_present( s, v0pina, false )
  //            assert_RefVal_for_LineText_is_present( s, v1pina, false )
  //            assert_RefVal_for_LineText_is_present( s, v0fasz, false )
  //            assert_RefVal_for_LineText_is_present( s, v1fasz, true )
  //          }
  //
  //          checkUpdatedState()
  //
  //          resetState( s, LabelOne )
  //
  //          checkInitState()
  //
  //        }
  //
  //      }
  //      val o = Test // needed so that object is evaluated
  //    }
  //    // 18cdc128d643445cb95a23e1e8a83ca0
  //
  //  }

}
