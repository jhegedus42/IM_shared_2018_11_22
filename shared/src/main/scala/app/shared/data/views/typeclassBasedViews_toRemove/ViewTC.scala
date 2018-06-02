package app.shared.data.views.typeclassBasedViews_toRemove

import io.circe.{Decoder, Encoder}
import io.circe.Encoder
import io.circe._
import io.circe.generic.semiauto._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

import scala.reflect.ClassTag






case class ViewName(name:String)



trait ViewTransferObject[V<:View[V]]{
  def getViewName()(implicit ct:ClassTag[V]):ViewName = {
    val s=ct.runtimeClass.getCanonicalName
    ViewName(s)
  }
}

//  def payload2JSON(pl:Payload)(implicit decoder: Encoder[Payload]):String
//    =decoder.apply(pl).spaces4

// Dyn reprezentacio a
// Req-nek es a
// Res-nak

object Todo {
  def ReqToDynReq = ???

  def DynReqToReq = ???
}

trait View[V<:View[V]]{
}

// View has Payload

// 1. type safe vs not type safe version + conversions between the two
// 2. transferable over wire + conversions
// 3. centralized/clustered, renaming View in IntelliJ requires additional changes,
//      name of View is only in Type parameters, not in any other identifiers


trait UserLineListView extends View[UserLineListView ]{
//  case class Payload(s:String)
//  case class Payload(s:String)

}

object Views{
  val userLineListView= new UserLineListView {}
}

object Test extends App{
}


object Test1 extends App{
  println("test1")
}