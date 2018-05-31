package app.shared.data.views.v3
import app.shared.data.views.v3.ViewParams.View1
import app.shared.data.views.v3.ViewParams.View1.View1
import app.shared.data.views.v3.ViewParams.View2.View2
//import org.scalajs.dom.experimental.ResponseType


trait View

trait ResponseType[V<:View]
trait RequestParType[V<:View]

trait ViewParams[V <: View]{
  type Response <:ResponseType[V]
  type RequestPar <: RequestParType[V]
}



object ViewParams {

  object View1{
    trait View1 extends View
    case class Resp(s:String) extends ResponseType[View1]
    case class Req(s:String) extends RequestParType[View1]

    trait ViewParamsInstanceView1 extends ViewParams[View1] {
      type Response = Resp
      type RequestPar = Req
    }

  }

  object View2 {

    trait View2 extends View
    case class Resp(s:String) extends ResponseType[View2]
    case class Req(s:Int) extends RequestParType[View2]

    trait ViewParamsInstanceView2 extends ViewParams[View2] {
      type Response = Resp
      type RequestPar = Req
    }

  }



}

trait ServerSideFun[C <: View] extends ViewParams[C] {
  def f(a: RequestPar): Response
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
