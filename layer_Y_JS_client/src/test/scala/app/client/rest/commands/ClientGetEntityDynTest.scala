package app.client.rest.commands

import app.client.rest.commands.generalCRUD.GetEntityAJAX.{ResDyn, getEntityDyn}
import app.shared.InvalidUUIDinURLError
import app.shared.data.model.LineText
import app.shared.data.ref.{Ref, RefValDyn, Version}
import app.testHelpersShared.data.TestEntities
import org.scalatest.{Assertion, AsyncFunSuite, Matchers}

import scala.concurrent.{ExecutionContextExecutor, Future}

/**
  * Created by joco on 16/12/2017.
  */
class ClientGetEntityDynTest extends AsyncFunSuite with Matchers with BeforeTester {


  implicit override def executionContext: ExecutionContextExecutor =
    scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

  //  before {
  //    //todonext setup and tear down before and after tests to re-set the state of the DB
  //    // 060432f52ebf4f129be15d8774f389a2
  //
  //  }

  def test1: Future[Assertion] = {
    // just like in AkkHttpServerTest ...
    //    val ref: Ref[Line] = Ref.makeWithUUID[Line]("4ce6fca0-0fd5-4197-a946-90f5e7e00d9d") // right
    val ref: Ref[LineText] =
      Ref.makeWithUUID[LineText]( "4ce6fca0-0fd5-4197-a946-90f5e7e00d9a" ) // wrong
//    getEntityDyn( ref ).map( (x: HttpGetEntityReqResultDyn) => {
//      println( "before checking getLines' result" );
//      println( "hello from test 1 after" )
//      val r: Assertion = x.erv.toEither.left.get shouldBe a[EntityDoesNotExistError]
//      r
//    } )
    ???
  }
// we gonna do this ... later...
/*  test( "test1 - entity does not exist error if entity does not exist with given uuid" )( test1 )

  testWithBefore( resetDBBeforeTest )(
    "test1 with before- entity does not exist error if entity does not exist with given uuid"
  )( test1 )*/

  testWithBefore( resetDBBeforeTest )( "incorrectly formatted uuid - return invalid uuid error" ) {
    // just like in AkkHttpServerTest ...
    //    val ref: Ref[Line] = Ref.makeWithUUID[Line]("4ce6fca0-0fd5-4197-a946-90f5e7e00d9d") // right
    val ref: Ref[LineText] =
      Ref.makeWithUUID[LineText]( "4ce6fca0-0fd5-4197-a946-90f5e7e0d9a" ) // wrong
    getEntityDyn( ref ).map( x => {
      println( "before checking getLines' result" );
      x.erv.toEither.left.get shouldBe a[InvalidUUIDinURLError]
    } )
  }

  testWithBefore( resetDBBeforeTest )( "happy path should return us a nice RefVal[Line]" ) {
    // just like in AkkHttpServerTest ...
    val ref: Ref[LineText] =
      Ref.makeWithUUID[LineText]( TestEntities.refValOfLineV0.r.uuid )
    // right
    //  val ref: Ref[Line] = Ref.makeWithUUID[Line]("4ce6fca0-0fd5-4197-a946-90f5e7e0d9a") // wrong

    def f2: Future[Assertion] =
      getEntityDyn( ref ).map((x: ResDyn) => {
        println( "before checking getLines' result" );
        x.erv.toEither.right.get shouldBe RefValDyn( ref, TestEntities.line, Version( 0 ) )
      } )
    f2
  }

}
