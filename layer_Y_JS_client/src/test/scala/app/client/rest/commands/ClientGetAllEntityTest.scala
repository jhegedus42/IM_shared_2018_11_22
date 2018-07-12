package app.client.rest.commands

import app.client.rest.commands.forTesting.Helpers
import app.client.rest.commands.generalCRUD.GetAllEntitiesAJAX
import app.shared.data.model.LineText
import app.shared.data.ref.RefVal
import app.testHelpersShared.data.{TestDataLabels, TestEntities}
import org.scalajs.dom.XMLHttpRequest
import org.scalatest.{Assertion, AsyncFunSuite, Matchers}

import scala.concurrent.{ExecutionContextExecutor, Future}

class ClientGetAllEntityTest extends AsyncFunSuite with Matchers with BeforeTester {

  implicit override def executionContext: ExecutionContextExecutor =
    scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

  testWithBefore( resetDBBeforeTest )( "getAllEntity should return a nice List[RefVal[LineText]]] on the happy path " ) {

    import io.circe.generic.auto._

    def f1: Future[XMLHttpRequest] =
      Helpers.resetServer( TestDataLabels.LabelOne )

    def f2: Future[Assertion] =
      GetAllEntitiesAJAX.getAllEntities[LineText].map(x => {
        val r: List[RefVal[LineText]] = x.toEither.right.get
        println("getAllEntity - test result:" + r)
        r shouldBe List( TestEntities.refValOfLineV0 )
      })

    f1.flatMap( _ => f2 ) // return a sequence of futures to be executed by the test runner

  }

}
