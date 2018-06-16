package app.shared.data.views.v4_path_dep_type_based_view_architecture.server.logic

import app.shared.data.views.v4_path_dep_type_based_view_architecture.shared.ArchitectureModel.{View, View1, View1$, View1_ViewParams_Instance, ViewParams}

trait ServerSideFunction[V<:View ]{
  val v:ViewParams[V]
  def f(i:v.Req):v.Resp
}

object ServerSideFunction{
  implicit object View1ServerSideImplicit extends ServerSideFunction[View1]{
    override val v = View1_ViewParams_Instance

    override def f(i: v.Req): v.Resp =
      View1_ViewParams_Instance.RespView1(i.s + " fax")
  }
}
