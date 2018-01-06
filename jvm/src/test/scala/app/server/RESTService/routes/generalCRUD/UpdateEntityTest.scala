package app.server.RESTService.routes.generalCRUD

import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.server.Route
import app.server.RESTService.RESTService
import app.server.RESTService.mocks.TestServerFactory
import app.server.RESTService.routes.RoutesTestBase
import app.server.State
import app.shared.model.LineText
import app.shared.model.ref.{Ref, RefVal, Version}
import app.shared.rest.routes_take3.crudCommands.UpdateEntityCommCommand
import app.shared.{EntityDoesNotExistError, EntityIsNotUpdateableError, InvalidUUIDinURLError, InvalidVersionError}
import app.testHelpersServer.state.TestData
import app.testHelpersShared.data.TestDataLabels.LabelOne
import app.testHelpersShared.data.TestEntities
import org.scalatest.Assertion

/**
  * Created by joco on 14/12/2017.
  */

//a4319006d1f949b08db39430fe80d8c6 commit 114ea387805bc68d16fa2ca25ef6648aa4a1fce4 Sun Dec 17 11:44:33 EET 2017

class UpdateEntityRouteTest extends
  RoutesTestBase with
  UpdateEntityTest{

  override def server(initState: State ): RESTService =
    TestServerFactory.getTestServer( initState )
}

trait UpdateEntityTest{
  this:RoutesTestBase=>

  trait UpdateTestSetup {

    val s:   RESTService   = server( TestData.getTestDataFromLabels( LabelOne ) )
    val r:   Route         = s.route
    val ref: Ref[LineText] = TestEntities.refToLine
    // ennek mar tartalmaznia kell a verziokat is
    val v1pina: RefVal[LineText] = TestEntities.refValOfLineV1

    import TestEntities.refValOfLineV0
    import monocle.macros.syntax.lens._

    val v0pina: RefVal[LineText] = refValOfLineV0
    val v0fasz: RefVal[LineText] = v0pina.lens( _.v.title ).set( Some( "fasz" ) )
    val v1fasz: RefVal[LineText] = v1pina.lens( _.v.title ).set( Some( "fasz" ) )

    def fun(): Unit

    def checkInitState(): Assertion = {
      assert_RefVal_for_LineText_is_present( s, v0pina, true )
      assert_RefVal_for_LineText_is_present( s, v1pina, false )
      assert_RefVal_for_LineText_is_present( s, v0fasz, false )
      assert_RefVal_for_LineText_is_present( s, v1fasz, false )
    }

    checkInitState()
    fun()
    s.shutdownActorSystem()
  }

  type Par=UpdateEntityCommCommand.UEC_Par[LineText]
  type Res=UpdateEntityCommCommand.UEC_Res[LineText]

  def updateRefValLine(
                        s:              RESTService,
                        refValToBeSent: RefVal[LineText],
                        assertion:      Res => Unit
                      ): Unit =
    {
      import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
      import io.circe.generic.auto._
      val r:   Route       = s.route

      val url : String = UpdateEntityCommCommand[LineText]().queryURL()
      val req: HttpRequest = Put(url, refValToBeSent)

      println( "Url:" + url )
      println( "Put request:" + req )

      val r2: Res = req ~> r ~> check {

        // ^^^ ez kell ide hogy a valaszt tudjuk dekodolni a kovetkezo sorban

        val result: Res = responseAs[Res]

        println( "response from Put request:" + result )
        assertion( result )
        result
      }

    }

  def happy_path=
    "update a RefVal[Line]  - if the URL and uuid and type and version is right - traits" in {
      object Test extends UpdateTestSetup {

        override
        def fun(): Unit = {
          updateRefValLine(s, v0fasz, result => {
            result.toEither.right.get shouldBe v1fasz
            println("what it should be :" + v1fasz)

          })
          assert_RefVal_for_LineText_is_present(s, v0pina, false)
          assert_RefVal_for_LineText_is_present(s, v1pina, false)
          assert_RefVal_for_LineText_is_present(s, v0fasz, false)
          assert_RefVal_for_LineText_is_present(s, v1fasz, true)
        }

      }
      val o = Test
    }

    "update entity route" should {
      happy_path



      "version incorrect - traits" in {
        object Test extends UpdateTestSetup {
          override val s: RESTService =
            server( TestData.TestState_LabelTwo_OneLine_WithVersionOne_nothing_else )

          override def fun(): Unit = {
            updateRefValLine( s, v0fasz, result => {
              result.toEither.left.get shouldBe
                InvalidVersionError( "while updating the state", Version( 1 ), Version( 0 ) )

            } )

            assert_RefVal_for_LineText_is_present( s, v1pina, true )
            assert_RefVal_for_LineText_is_present( s, v0pina, false )
            assert_RefVal_for_LineText_is_present( s, v0fasz, false )
            assert_RefVal_for_LineText_is_present( s, v1fasz, false )

          }

          val o = Test
        }
      }

      "happy path - update a RefVal[Line]  - if the URL and uuid and type and version is right" in {

        runWithServer( server( TestData.TestState_LabelOne_OneLine_WithVersionZero_nothing_else ) ) {
          s =>
            val r: Route = s.route

            val ref: Ref[LineText] = TestEntities.refToLine // ennek mar tartalmaznia kell a verziokat is
            import TestEntities.{refValOfLineV0 => v0pina, refValOfLineV1 => v1pina}
            import monocle.macros.syntax.lens._

            val v0fasz: RefVal[LineText] =
              v0pina.lens( _.v.title ).set( Some( "fasz" ) )
            val v1fasz: RefVal[LineText] =
              v1pina.lens( _.v.title ).set( Some( "fasz" ) )

            assert_RefVal_for_LineText_is_present( s, v0pina, true )
            assert_RefVal_for_LineText_is_present( s, v1pina, false )
            assert_RefVal_for_LineText_is_present( s, v0fasz, false )
            assert_RefVal_for_LineText_is_present( s, v1fasz, false )

            updateRefValLine( s, v0fasz, result => {
              result.toEither.right.get shouldBe v1fasz
              println( "what it should be :" + v1fasz )

            } )

            assert_RefVal_for_LineText_is_present( s, v0pina, false )
            assert_RefVal_for_LineText_is_present( s, v1pina, false )
            assert_RefVal_for_LineText_is_present( s, v0fasz, false )
            assert_RefVal_for_LineText_is_present( s, v1fasz, true )
        }

      }

      //  type error ...  ???
      "return EntityDoesNotExistError if uuid is correct but no Line exists with that UUID wrong" in {

        import io.circe.generic.auto._

        // what does it mean that URL is shitty => if the UUID is shitty, incorrectly formatted
        //get a Ref with bad uuid

  //      val url: String =
  //        UpdateEntityURL( EntityType.make[LineText] ).clientPathWithSlashWithoutHost.asString
//        val url : String = ???
        val url : String = UpdateEntityCommCommand[LineText]().queryURL()
        import monocle.macros.syntax.lens._
        val rv: RefVal[LineText] = TestEntities.refValOfLineV0

        val s: RESTService =
          server( TestData.TestState_LabelOne_OneLine_WithVersionZero_nothing_else )
        val r: Route = s.route

        import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
        val req = Put( url, rv.lens( _.r.uuid ).set( TestEntities.dummyUUID2 ) )

        println( req )

        req ~> r ~> check {

          // ^^^ ez kell ide hogy a valaszt tudjuk dekodolni a kovetkezo sorban

          val r: Res = responseAs[Res]

          println( r )
          r.toEither.left.get
            .asInstanceOf[EntityIsNotUpdateableError]
            .edne
            .get shouldBe a[EntityDoesNotExistError]

        }
        s.shutdownActorSystem()

      }

      "version incorrect" in {
        val s: RESTService =
          server( TestData.TestState_LabelTwo_OneLine_WithVersionOne_nothing_else )
        val r: Route = s.route

        val ref: Ref[LineText] = TestEntities.refToLine // ennek mar tartalmaznia kell a verziokat is
        import TestEntities.{refValOfLineV0 => v0pina, refValOfLineV1 => v1pina}
        import monocle.macros.syntax.lens._

        val v0fasz: RefVal[LineText] = v0pina.lens( _.v.title ).set( Some( "fasz" ) )
        val v1fasz: RefVal[LineText] = v1pina.lens( _.v.title ).set( Some( "fasz" ) )

        assert_RefVal_for_LineText_is_present( s, v1pina, true )
        assert_RefVal_for_LineText_is_present( s, v0pina, false )
        assert_RefVal_for_LineText_is_present( s, v0fasz, false )
        assert_RefVal_for_LineText_is_present( s, v1fasz, false )

        updateRefValLine( s, v0fasz, result => {
          result.toEither.left.get shouldBe InvalidVersionError(
            "while updating the state",
            Version( 1 ),
            Version( 0 )
          )

        } )

        assert_RefVal_for_LineText_is_present( s, v1pina, true )
        assert_RefVal_for_LineText_is_present( s, v0pina, false )
        assert_RefVal_for_LineText_is_present( s, v0fasz, false )
        assert_RefVal_for_LineText_is_present( s, v1fasz, false )
        s.shutdownActorSystem()
      }

      "return an InvalidURLError if UUID in URL is incorrect" in {

        import io.circe.generic.auto._

        // what does it mean that URL is shitty => if the UUID is shitty, incorrectly formatted
        //get a Ref with bad uuid

        import monocle.macros.syntax.lens._
        val ref: Ref[LineText] = Ref.make[LineText]().lens( _.uuid.id ).set( "fasz" )
        //fuking up the uuid - on purpose
  //      val url: String =
  //        UpdateEntityURL( EntityType.make[LineText] ).clientPathWithSlashWithoutHost.asString

//        val url : String = ???
        val url : String = UpdateEntityCommCommand[LineText]().queryURL()
        println( url )
        val s: RESTService =
          server( TestData.TestState_LabelOne_OneLine_WithVersionZero_nothing_else )
        val r: Route = s.route

        val rv: RefVal[LineText] = TestEntities.refValOfLineV0
        println( rv )
        import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
        val req = Put( url, rv.lens( _.r.uuid.id ).set( "fasz" ) )

        println( req )

        req ~> r ~> check {

          // ^^^ ez kell ide hogy a valaszt tudjuk dekodolni a kovetkezo sorban

          val r: Res = responseAs[Res]

          println( r )
          r.toEither.left.get shouldBe a[InvalidUUIDinURLError]

        }
        s.shutdownActorSystem()

      }
    }

}
