package app.shared.data.views.v2_toRemove

import io.circe.generic.auto._

object RunTest extends App{
  println("bla")

  val pl1   = ViewTypeClass.UserLineListViewTCInstance.ViewResponsePayloadImpl("bla")
  val pl1_s: String          = ViewTypeClass.viewRespToJSON(pl1)
  println(pl1_s)

}

