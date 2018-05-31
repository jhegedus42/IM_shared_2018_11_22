package app.shared.data.views.v3
import app.shared.data.views.v3.ViewParams.View1
import app.shared.data.views.v3.ViewParams.View1.View1
import app.shared.data.views.v3.ViewParams.View2.View2
//import org.scalajs.dom.experimental.ResponseType
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


trait View

trait Response[V<:View]
trait RequestParameters[V<:View]

trait ViewParams[V <: View]{
  type ResponseType <:Response[V]
  type RequestParametersType <: RequestParameters[V]
}



object ViewParams {

  object View1{
    trait View1 extends View
    case class Resp(s:String) extends Response[View1]
    case class Req(s:String) extends RequestParameters[View1]

    trait ViewParamsInstanceView1 extends ViewParams[View1] {
      type ResponseType = Resp
      type RequestParametersType = Req
    }

  }

  object View2 {

    trait View2 extends View
    case class Resp(s:String) extends Response[View2]
    case class Req(s:Int) extends RequestParameters[View2]

    trait ViewParamsInstanceView2 extends ViewParams[View2] {
      type ResponseType = Resp
      type RequestParametersType = Req
    }

  }



}

trait ServerSideFun[C <: View] extends ViewParams[C] {
  def f(a: RequestParametersType): ResponseType
}

object ServerSideFun {
  implicit object View1Conv
    extends ServerSideFun[View1]
            with ViewParams.View1.ViewParamsInstanceView1 {

    override def f(a: ViewParams.View1.Req): ViewParams.View1.Resp = ViewParams.View1.Resp("bla")
  }

  implicit object View2Conv
    extends ServerSideFun[View2]
            with ViewParams.View2.ViewParamsInstanceView2 {

    override def f(a: ViewParams.View2.Req): ViewParams.View2.Resp = ViewParams.View2.Resp("bla")
  }

}

object JSONConvert
{
  def encodeResponseToJSON[R<:Response[_]](response: R)(implicit encoder: Encoder[R] ): String = {
    response.asJson.spaces4
  } //to be used by server, before sending the response over the wire
def encodeRequestToJSON[R<:RequestParameters[_]](response: R)(implicit encoder: Encoder[R] ): String = {
  response.asJson.spaces4
} //to be used by client, before sending the request over the wire


}

object TestJSONConvert extends App{

    println("fax")
  val v1res=View1.Resp("bla")
  val sres=JSONConvert.encodeResponseToJSON(v1res)
  println(sres)
  val v1req=View1.Req("req")
  val sreq=JSONConvert.encodeRequestToJSON(v1req)
  println(sreq)

}
