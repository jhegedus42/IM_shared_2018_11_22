package app.client.rest.commands

import app.client.rest.commands.customCommands.GetUserLineListAJAX
import app.client.rest.commands.forTesting.Helpers
import app.client.rest.commands.generalCRUD.GetAllEntitiesAJAX
import app.shared.data.model.{User, UserLineList}
import app.shared.data.ref.{Ref, RefVal}
import app.testHelpersShared.data.{TestDataLabels, TestEntities, TestEntitiesForStateThree}
import org.scalajs.dom.XMLHttpRequest
import org.scalatest.{Assertion, AsyncFunSuite, Matchers}

import scala.concurrent.{ExecutionContextExecutor, Future}

/**
  * Created by joco on 16/12/2017.
  */
class ClientGetUserLineListsTest extends AsyncFunSuite with Matchers with BeforeTester {
  //
  //
  // ===> 1.3 <==== task-completed  1.3 make ClientGetAllEntityTest pass
  // 08e1a06b44c44897a2c900a50bd143dc commit 020a9f730a57689fe687d0bc94b658e6e9cab554 Fri Dec 15 13:25:08 EET 2017


  val resBe:  Seq[RefVal[UserLineList]] = List(TestEntitiesForStateThree.listRV)

  val resNotBe: Seq[RefVal[UserLineList]] =  List( )

  val u: Ref[User] = TestEntitiesForStateThree.userRef
  implicit override def executionContext: ExecutionContextExecutor =
    scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

  testWithBefore( resetDBBeforeTest )( "get all user line lists  should work" ) {
    // just like in AkkHttpServerTest ...

    import io.circe.generic.auto._

    def f1: Future[XMLHttpRequest] =
      Helpers.resetServer( TestDataLabels.LabelThree  )

    def f2: Future[Assertion] =
      GetUserLineListAJAX.getUserLineLists(u).map(x => {
        val entities: List[RefVal[UserLineList]] = x.toEither.right.get
        entities shouldBe resBe
        entities should not be resNotBe
      })

    f1.flatMap( _ => f2 ) // return a sequence of futures to be executed by the test runner
//2fc4960b35544a50a1da8aed687c5baa commit 52318d6d50de2cef2b36af867540c3464d6305cf Tue Oct 24 13:20:34 EEST 2017

  }

}
