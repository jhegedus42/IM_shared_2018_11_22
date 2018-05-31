package app.shared.data.views.v2

import app.shared.data.views.UserLineListView
import app.shared.data.views.v2.ViewTypeClass.UserLineListViewTCInstance.{ResponsePld, ViewResponsePayloadImpl}
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
  type ParamsPld <: ViewRequestParamsPayload
}

sealed trait  View
trait         UserLineListView extends View
case class    ViewName(name:String)

trait         ViewResponsePayLoad{
  val payloadType="Response"
}

trait         ViewRequestParamsPayload


object ViewTypeClass{
  type Aux[RespPL, ParPL, V<:View] =
      ViewTypeClass[V] {
     type ResponsePld = RespPL
     type ParamsPld = ParPL
  }

  def viewRespToJSON(responsePld: ResponsePld)(implicit encoder: Encoder[ResponsePld]) : String = {
    responsePld.asJson.spaces4
  } // this is the same for all instances


    implicit object UserLineListViewTCInstance extends UserLineListViewTCInstanceTrait

    trait UserLineListViewTCInstanceTrait extends ViewTypeClass[UserLineListView] {


    case class ViewResponsePayloadImpl(kamuPayload:String) extends ViewResponsePayLoad {
    }

    case class ViewRequestParamsPayloadImpl(kamuPayload:String) extends ViewRequestParamsPayload {
    }

    override type ResponsePld = ViewResponsePayloadImpl
    override type ParamsPld   = ViewRequestParamsPayloadImpl

    // we need a type class that takes a ViewParameters and returns a ViewPayload - the business logic
  }
}


object Views{
  def getViewName[V<:View]()(implicit ct:ClassTag[V]):ViewName = {
    val s=ct.runtimeClass.getCanonicalName
    ViewName(s)
  }
}





