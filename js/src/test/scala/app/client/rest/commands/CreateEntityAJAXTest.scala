package app.client.rest.commands

import app.client.rest.commands.forTesting.Helpers
import app.client.rest.commands.generalCRUD.{CreateEntityAJAX, GetAllEntitiesAJAX}
import app.shared.SomeError_Trait
import app.shared.data.model.LineText
import app.shared.data.ref.RefVal
import app.shared.rest.routes.crudCommands.CreateEntityCommCommand.CEC_Res
import app.shared.rest.routes.crudCommands.GetAllEntitiesCommand
import app.testHelpersShared.data.TestDataLabels
import io.circe.generic.auto._
import org.scalajs.dom.XMLHttpRequest

import scala.concurrent.Future
import scalaz.\/
import org.scalatest.{Assertion, AsyncFunSuite, Matchers}
//import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

/**
  * Created by joco on 16/12/2017.
  */

class CreateEntityAJAXTest extends AsyncFunSuite with Matchers with BeforeTester {

  implicit override def executionContext =
    scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

  // ^^^^^ https://github.com/scalatest/scalatest/issues/1039

  testWithBefore( resetDBBeforeTest )( "createEntity should return a nice RefVal[Line] on the happy path " ) {
    // just like in AkkHttpServerTest ...

    def reset: Unit = {
      def f1: Future[XMLHttpRequest] =
        Helpers
          .resetServer( TestDataLabels.LabelOne )
      f1
    }

    type ResLocal = GetAllEntitiesCommand.gAEsLineText.Result

    val l = LineText( title =  "macska" ,text="test" )

    def createLine: Future[CEC_Res[LineText]] = CreateEntityAJAX.createEntity(l)

    def contains(a1: \/[SomeError_Trait, List[RefVal[LineText]]], lt: CEC_Res[LineText] ): Boolean = {
      val a: List[RefVal[LineText]] = a1.toEither.right.get
      val b: Boolean                = a.contains( lt.toEither.right.get )
      b
    }
                                                                                                               def debugPrint[E](e:E):E={println(e);e}
//    import sext._

    def res: Future[Assertion] = for {
      al1 <- GetAllEntitiesAJAX.getAllEntities[LineText]
      lc: CEC_Res[LineText] <- createLine
      al2 <- GetAllEntitiesAJAX.getAllEntities[LineText]
      _ <- Future({println("al1="+al1,"al2="+al2)})
//      _ <- Future({println("al1="+al1.treeString,"al2="+al2.treeString)})

      b1 = (contains( al2, lc ) )
      b2 = !contains( al1, lc )
    } yield (b1 && b2 shouldBe true)

    res
  }

}
