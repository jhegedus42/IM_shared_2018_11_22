package app.shared.data.views.v2

import app.shared.data.views.v2.ViewTypeClass.UserLineListViewTCInstance.{ResponsePayload, ResponsePld}
import io.circe.Encoder
import io.circe.Encoder
import io.circe.generic.auto._
import io.circe.{Decoder, Encoder}
import io.circe.Encoder
import io.circe._
import io.circe.generic.semiauto._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

import scala.reflect.ClassTag

trait ViewTypeClass[V<:View] {
  type ResponsePld <:ViewResponsePayLoad
  type ParamsPld <: ViewParamsPayload

}

sealed trait  View
trait         UserLineListView extends View
case class    ViewName(name:String)
trait         ViewResponsePayLoad{
  val payloadType="Response"
}
trait         ViewParamsPayload


object ViewTypeClass{
  type Aux[RespPL, ParPL, V<:View] =
      ViewTypeClass[V] {
     type ResponsePld = RespPL
     type ParamsPld = ParPL
  }

  def RespToJSON(responsePld: ResponsePld)(implicit encoder: Encoder[ResponsePld]) : String = {
    responsePld.asJson.spaces4
  }

  implicit object UserLineListViewTCInstance extends ViewTypeClass[UserLineListView] {


    case class ResponsePayload(kamuPayload:String) extends ViewResponsePayLoad {
    }

    case class ParamsPayload(kamuPayload:String) extends ViewParamsPayload {
    }

    override type ResponsePld = ResponsePayload
    override type ParamsPld = ParamsPayload

    // we need a type class that takes a ViewParameters and returns a ViewPayload - the business logic
  }
}


object Views{
  def getViewName[V<:View]()(implicit ct:ClassTag[V]):ViewName = {
    val s=ct.runtimeClass.getCanonicalName
    ViewName(s)
  }
}





