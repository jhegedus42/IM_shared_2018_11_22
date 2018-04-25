package app.shared.data.views

import app.shared.data.views.UserLineListView2.ViewPayLoad

/**
  * Created by joco on 08/01/2018.
  */
trait View {
  type PayLoad <:ViewPayLoadType
}

trait ViewPayLoadType {

}

object UserLineListView2 extends View {
  case class ViewPayLoad(kamuPayload:String) extends ViewPayLoadType {
  }

  // we need a type class that takes a ViewParameters and returns a ViewPayload - the business logic
}

object TestStuff extends App{
 println("bla")
  val pl1 = UserLineListView2.ViewPayLoad("bla")
}

object ConversionOnServerSide{

  //  payload to json
  //  json to parameters

}

