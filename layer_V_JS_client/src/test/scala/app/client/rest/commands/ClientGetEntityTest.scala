package app.client.rest.commands

import app.client.rest.commands.generalCRUD.GetEntityAJAX.getEntity
import app.client.rest.commands.forTesting.Helpers
import app.shared.data.model.LineText
import app.shared.data.ref.{Ref, RefVal, Version}
import app.shared.{EntityDoesNotExistError, InvalidUUIDinURLError}
import app.testHelpersShared.data.{TestDataLabels, TestEntities}
import org.scalajs.dom.XMLHttpRequest

import scala.concurrent.{ExecutionContextExecutor, Future}
import org.scalatest.{Assertion, AsyncFunSuite, Matchers}
/**
  * Created by joco on 16/12/2017.
  */
class ClientGetEntityTest extends AsyncFunSuite with Matchers with BeforeTester {
  // artoshitnasstioastiaehrohnts
  implicit override def executionContext: ExecutionContextExecutor =
    scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

  println( "42" )

  testWithBefore( resetDBBeforeTest )(
    "getEntity should return does not exist error if entity does not exist with given uuid"
  ) {
    // just like in AkkHttpServerTest ...
    //    val ref: Ref[Line] = Ref.makeWithUUID[Line]("4ce6fca0-0fd5-4197-a946-90f5e7e00d9d") // right
    val ref: Ref[LineText] =
      Ref.makeWithUUID[LineText]( "4ce6fca0-0fd5-4197-a946-90f5e7e00d9a" ) // wrong
    import io.circe.generic.auto._
    getEntity[LineText]( ref ).map( x => {
      println( "before checking getLines' result" );
      x.toEither.left.get shouldBe a[EntityDoesNotExistError]
    } )
  }

  testWithBefore( resetDBBeforeTest )(
    "getEntity should return invalid uuid error if the uuid is invalidly formatted"
  ) {
    // just like in AkkHttpServerTest ...
    //    val ref: Ref[Line] = Ref.makeWithUUID[Line]("4ce6fca0-0fd5-4197-a946-90f5e7e00d9d") // right
    val ref: Ref[LineText] =
      Ref.makeWithUUID[LineText]( "4ce6fca0-0fd5-4197-a946-90f5e7e0d9a" ) // wrong
    import io.circe.generic.auto._
    getEntity[LineText]( ref ).map( x => {
      println( "before checking getLines' result" );
      x.toEither.left.get shouldBe a[InvalidUUIDinURLError]
    } )
  }

  testWithBefore( resetDBBeforeTest )( "getEntity should return a nice RefVal[Line] on the happy path " ) {
    // just like in AkkHttpServerTest ...
    val ref: Ref[LineText] =
      Ref.makeWithUUID[LineText]( TestEntities.refValOfLineV0.r.uuid )
    //  val ref: Ref[Line] = Ref.makeWithUUID[Line]("4ce6fca0-0fd5-4197-a946-90f5e7e0d9a") // wrong
    import io.circe.generic.auto._

    def f1: Future[XMLHttpRequest] =
      Helpers.resetServer( TestDataLabels.LabelOne )
    def f2: Future[Assertion] =
      getEntity[LineText]( ref ).map( x => {
        println( "before checking getLines' result" );
        x.toEither.right.get shouldBe RefVal( ref, TestEntities.line, Version( 0 ) )
      } )

    f1.flatMap( _ => f2 )

  }


}
