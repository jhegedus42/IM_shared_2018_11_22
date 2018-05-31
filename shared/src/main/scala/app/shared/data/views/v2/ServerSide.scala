package app.shared.data.views.v2
import app.shared.data.views.v2.ViewTypeClass.{Aux, UserLineListViewTCInstance, UserLineListViewTCInstanceTrait}

/**
  * Created by joco on 04/05/2018.
  */
object ServerSide {

  // have some type class that returns some stuff when called by the rest route ...
  def handleViewReadRequest[V<:View,Req,Resp]
      (                       req:  Req)
      (implicit requestHandleable:  RequestHandleable[V],
                    viewTypeClass:  ViewTypeClass.Aux[Resp,Req,V]   ) : Resp = ???
                      // requestHandleable.handleRequest(req)
}


trait RequestHandleable[V<:View] extends ViewTypeClass[V] {
  def handleRequest
    (req: ParamsPld)
    (implicit viewTypeClass: Aux[ResponsePld, ParamsPld, UserLineListView]): ResponsePld = ???
}

object RequestHandleable {
  implicit object RequestHandleableInstance extends RequestHandleable[UserLineListView] with UserLineListViewTCInstanceTrait
    {
      override def handleRequest
        (req: ParamsPld)
        (implicit viewTypeClass: Aux[ResponsePld, ParamsPld, UserLineListView]): ResponsePld = ???
   }

}

