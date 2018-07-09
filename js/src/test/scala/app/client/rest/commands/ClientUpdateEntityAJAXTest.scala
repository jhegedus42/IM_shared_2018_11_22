package app.client.rest.commands

import app.shared.data.model.LineText
import app.shared.data.ref.{Ref, RefVal, Version}
import app.shared.rest.routes.crudCommands.UpdateEntityCommCommand
import app.shared.{EntityDoesNotExistError, EntityIsNotUpdateableError, InvalidUUIDinURLError, SomeError_Trait}

import scala.concurrent.{ExecutionContextExecutor, Future}
import org.scalatest.{Assertion, AsyncFunSuite, Matchers}
/**
  * Created by joco on 16/12/2017.
  */

// 1dac85fe39534d7dbb4f5b12a0c9d31f commit 4feef4a141645610078cff9336263f90362a31b1 Sun Dec 17 06:31:00 EET 2017

class ClientUpdateEntityAJAXTest extends AsyncFunSuite with BeforeTester with Matchers {
  // stronertoaeantsnieshiastoa
  import scala.scalajs.js.Dynamic

  lazy val jsdom: Dynamic = Dynamic.global.window

  import app.client.rest.commands.generalCRUD.UpdateEntityAJAX._

  implicit override def executionContext: ExecutionContextExecutor =
    scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

  type Res = UpdateEntityCommCommand.UEC_Res[LineText]

  test(
    "return InvalidUUIDinURLError if uuid" +
      " is incorrect"
  ) {
    println( "client update entity test start" )
    import io.circe.generic.auto._

    // what does it mean that URL is shitty => if the UUID is shitty, incorrectly formatted
    //get a Ref with bad uuid
    import monocle.macros.syntax.lens._

    val ref: Ref[LineText] = Ref
      .make[LineText]().lens( _.uuid.id )
      .set( "4ce6fca0-0fd5-4197-a946-90f5e7e009e" )
    //fuking up the uuid - on purpose
//    val url: String = UpdateEntityURL( ref.et ).clientPathWithSlashWithoutHost.asString
    val url:       String              = UpdateEntityCommCommand[LineText]().queryURL()
//    val url: String = ???
    println( url )
    val l = LineText( title =  "macska" ,text="test" )
    val rv: RefVal[LineText] = RefVal[LineText]( ref, l, Version( 0 ) )
    println( "client update entity test end" )

    def r: Future[Assertion] =
      updateEntity( rv ).map( (x: Res) => {
        println( "before checking updateEntity' result, that it is InvalidUUIDinURLError" );
        val e: SomeError_Trait =
          x.toEither.left.get.asInstanceOf[SomeError_Trait]
        e shouldBe a[InvalidUUIDinURLError] // ez az assertion véd, be van élesítve
      } )
    r
  }

  test( "return RefVal - the happy path for PUT" ) {
    println( "client update entity test start" )
    import io.circe.generic.auto._

    // what does it mean that URL is shitty => if the UUID is shitty, incorrectly formatted
    //get a Ref with bad uuid

    import monocle.macros.syntax.lens._
    val ref: Ref[LineText] =
      Ref
        .make[LineText]().lens( _.uuid.id )
        .set( "4ce6fca0-0fd5-4197-a946-90f5e7e00d9d" )
    val url:       String              = UpdateEntityCommCommand[LineText]().queryURL()

    println( url )
    val l = LineText( title =  "macska" ,text="test" )
    val rv: RefVal[LineText] = RefVal[LineText]( ref, l, Version( 0 ) )
    println( "client update entity test end" )
    //set title random  to random number
    //chain Futures

    def r: Future[Assertion] =
      updateEntity( rv ).map( (x: Res) => {
        println( "before checking updateEntity' result, that it is the right RefVal" );
        x.toEither.right.get shouldBe RefVal( ref, LineText( title =  "macska" ,text="test" ), Version( 1 ) ) // ez az assertion véd, be van élesítve
      } )
    r
  }

  test(
    "return EntityDoesNotExistError if uuid" +
      " is correct but no Line exists with that UUID wrong"
  ) {
    println( "client update entity test start" )
    import io.circe.generic.auto._

    // what does it mean that URL is shitty => if the UUID is shitty, incorrectly formatted
    //get a Ref with bad uuid

    import monocle.macros.syntax.lens._
    val ref: Ref[LineText] = Ref
      .make[LineText]()
      .lens( _.uuid.id )
      .set( "4ce6fca0-0fd5-4197-a946-90f5e7e00d9e" )

    //fuking up the uuid - on purpose
//    val url: String = UpdateEntityURL( ref.et ).clientPathWithSlashWithoutHost.asString

            val url:       String              = UpdateEntityCommCommand[LineText]().queryURL()
//    val url: String = ???
    println( url )
    val l = LineText( title =  "macska" ,text="test" )
    val rv: RefVal[LineText] = RefVal[LineText]( ref, l, Version( 0 ) )
    println( "client update entity test end" )

    def r: Future[Assertion] =
      updateEntity( rv ).map( (x: Res) => {
        println( "before checking getLines' result" );
        val e: EntityIsNotUpdateableError =
          x.toEither.left.get.asInstanceOf[EntityIsNotUpdateableError]
        e.edne.head shouldBe a[EntityDoesNotExistError]
        // ez az assertion véd, be van élesítve
        x.toEither.left.get shouldBe a[EntityIsNotUpdateableError]
      } )
    r
  }

}
