package app.shared.data.views.v2

import io.circe.generic.auto._

object RunTest extends App{
  println("bla")

  val pl1   = ViewTypeClass.UserLineListViewTCInstance.ResponsePayload("bla")
  val pl1_s: String          = ViewTypeClass.RespToJSON(pl1)
  println(pl1_s)

}

