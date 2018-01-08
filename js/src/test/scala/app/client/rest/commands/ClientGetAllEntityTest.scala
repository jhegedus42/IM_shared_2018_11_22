package app.client.rest.commands

import app.client.rest.commands.forTesting.Helpers
import app.client.rest.commands.generalCRUD.GetAllEntitiesAJAX
import app.shared.model.entities.LineText
import app.shared.model.ref.RefVal
import app.testHelpersShared.data.{TestDataLabels, TestEntities}
import org.scalajs.dom.XMLHttpRequest
import org.scalatest.{Assertion, AsyncFunSuite, Matchers}

import scala.concurrent.{ExecutionContextExecutor, Future}

/**
  * Created by joco on 16/12/2017.
  */
class ClientGetAllEntityTest extends AsyncFunSuite with Matchers with BeforeTester {
  //
  //
  // ===> 1.3 <==== task-completed  1.3 make ClientGetAllEntityTest pass
  // 08e1a06b44c44897a2c900a50bd143dc commit 020a9f730a57689fe687d0bc94b658e6e9cab554 Fri Dec 15 13:25:08 EET 2017


  implicit override def executionContext: ExecutionContextExecutor =
    scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

  testWithBefore( resetDBBeforeTest )( "getAllEntity should return a nice List[RefVal[LineText]]] on the happy path " ) {
    // just like in AkkHttpServerTest ...

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
//2fc4960b35544a50a1da8aed687c5baa commit 52318d6d50de2cef2b36af867540c3464d6305cf Tue Oct 24 13:20:34 EEST 2017

  }

}
