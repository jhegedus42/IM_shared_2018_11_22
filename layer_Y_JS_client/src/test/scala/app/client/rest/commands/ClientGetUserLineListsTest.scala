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

// we gonna complete this ... one day ....
class ClientGetUserLineListsTest extends AsyncFunSuite with Matchers with BeforeTester {

  val resBe:  Seq[RefVal[UserLineList]] = List(TestEntitiesForStateThree.listRV)

  val resNotBe: Seq[RefVal[UserLineList]] =  List( )

  val u: Ref[User] = TestEntitiesForStateThree.userRef
  implicit override def executionContext: ExecutionContextExecutor =
    scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

  testWithBefore( resetDBBeforeTest )( "get all user line lists  should work" ) {

    import io.circe.generic.auto._

    def f1: Future[XMLHttpRequest] =
      Helpers.resetServer( TestDataLabels.LabelThree  )

    def f2: Future[Assertion] = ???

//      GetUserLineListAJAX.getUserLineLists(u).map(x => {
////        val entities: List[RefVal[UserLineList]] = x.toEither.right.get.lists
//        val entities: List[RefVal[UserLineList]] = ???
//        entities shouldBe resBe
//        entities should not be resNotBe
//      })

    f1.flatMap( _ => f2 ) // return a sequence of futures to be executed by the test runner

  }

}
