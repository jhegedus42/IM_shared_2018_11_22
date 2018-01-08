package app.testHelpersServer.state

import app.server.State
import app.shared.SomeError_Trait
import app.shared.model.entities.LineText
import app.shared.model.ref.{RefVal, Version}
import app.shared.model.utils.PrettyPrint
import app.testHelpersShared.data.TestDataLabels.{LabelOne, LabelThree, LabelTwo, TestDataLabel}

import scalaz.\/


  object TestData {

    def getTestDataFromLabels(label:TestDataLabel):State ={
     // 847fdd932e9f4a5ea7443fbb406708b9
      label match {
        case LabelOne => TestState_LabelOne_OneLine_WithVersionZero_nothing_else
        case LabelTwo => TestState_LabelTwo_OneLine_WithVersionOne_nothing_else
        case LabelThree => StateThree.state
      }
    }

    import app.testHelpersShared.data.TestEntities.refValOfLineV0
    val TestState_LabelOne_OneLine_WithVersionZero_nothing_else:  State =
      State().insertEntity(refValOfLineV0).toEither.right.get //todolater, megszabadulni a get-tol
//    2f438d1234954a1c955f15ee0f6f4774 commit 31f16c038c2479d05291456e73c3490508aa8591 Sat Oct 21 01:42:07 EEST 2017

    object LabelOneEntities{
      val lineInState: RefVal[LineText] = refValOfLineV0
    }


    val TestState_LabelTwo_OneLine_WithVersionOne_nothing_else:  State = {
      val res = TestState_LabelOne_OneLine_WithVersionZero_nothing_else.updateEntity(refValOfLineV0)
      res.toEither.right.get._1 //todolater megszabadulni a get-tol
    }


  }

object StateThree extends App {
  import app.testHelpersShared.data.TestEntitiesForStateThree._

  def step1=State()
  def step2: \/[SomeError_Trait, State] = {
    step1.insertEntity(rvdUser)
  }

  def s6val(): \/[SomeError_Trait, State] = for {
    s2 <- State().insertEntity(rvdUser)
    s3 <- s2.insertEntity(RefVal(refLine1, line1, Version()))
    s4 <- s3.insertEntity(RefVal(refLine2, line2, Version()))
    s5 <- s4.insertEntity(RefVal(refLine3, line3, Version()))
    s6 <- s5.insertEntity(RefVal(listRef, list, Version()))
  } yield (s6)

  def state: State = {
    val s= s6val()
    val res=s.toEither.right.get
    res
  }
  def printState() = {
    lazy val res: String = PrettyPrint.prettyPrint(state.stateMap.toList)
    //  println(res)
  }

}


