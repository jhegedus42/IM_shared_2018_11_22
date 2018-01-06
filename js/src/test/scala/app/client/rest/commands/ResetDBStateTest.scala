package app.client.rest.commands

import app.client.rest.commands.generalCRUD.GetEntityAJAX.getEntity
import app.client.rest.commands.forTesting.Helpers
import app.shared.model.LineText
import app.shared.model.ref.{Ref, RefVal, Version}
import app.testHelpersShared.data.{TestDataLabels, TestEntities}
import org.scalatest.{Assertion, AsyncFunSuite, FunSuite, Matchers}

import scala.concurrent.{ExecutionContextExecutor, Future}

/**
  * Created by joco on 16/12/2017.
  */
class ResetDBStateTest extends AsyncFunSuite with Matchers with BeforeTester {
  //43083bb7a1534700bafe4e611def9a72
  implicit override def executionContext: ExecutionContextExecutor =
    scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

  def checkDBState(version: Version ): Future[Assertion] = {
    val ref: Ref[LineText] =
      Ref.makeWithUUID[LineText]( TestEntities.refValOfLineV0.r.uuid )
    import io.circe.generic.auto._

    def f2: Future[Assertion] =
      getEntity[LineText]( ref ).map( x => {
        println( "before checking getLines' result" );
        val res=x.toEither.right.get
        println("checkDBState:"+res)
         res shouldBe RefVal( ref, TestEntities.line, version )
      } )
    f2
  }

  test( "reset db call should work" ) {
    for {
      _ <- Helpers.resetServer( TestDataLabels.LabelOne )
      _ <- checkDBState( Version( 0 ) )

      _ <- Helpers.resetServer( TestDataLabels.LabelTwo )
      _ <- checkDBState( Version( 1 ) )

      _ <- Helpers.resetServer( TestDataLabels.LabelOne )
      _ <- checkDBState( Version( 0 ) )

    } yield (succeed)
  }
}
