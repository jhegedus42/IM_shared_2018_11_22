package app.shared.data.views.v3_view_architecture_design.server.stateAccess.viewQueries

import app.shared.data.views.v3_view_architecture_design.shared.views.View1_HolderObject.View1
import app.shared.data.views.v3_view_architecture_design.shared.views.View2_HolderObject.View2
import app.shared.data.views.v3_view_architecture_design.shared.views.common.{View, ViewParams}
import app.shared.data.views.v3_view_architecture_design.shared.views.{View1_HolderObject, View2_HolderObject}

/**
  * Created by joco on 02/06/2018.
  */


object ServerSideFun {
  implicit object View1Conv
    extends ServerSideFun[View1]
            with View1_HolderObject.ViewParamsInstanceView1 {

    override def f(a: View1_HolderObject.ReqParams_View1): View1_HolderObject.Response_View1 = View1_HolderObject.Response_View1("bla")
  }

  implicit object View2Conv
    extends ServerSideFun[View2]
            with View2_HolderObject.ViewParamsInstanceView2 {

    override def f(a: View2_HolderObject.ReqParams_View2): View2_HolderObject.Response_View2 = View2_HolderObject.Response_View2("bla")
  }

}

trait ServerSideFun[C <: View] extends ViewParams[C] {
  def f(a: RequestParametersType): ResponseType
}

object TestServerSideFun{


  // let's create a function
  // 1) to which when we pass a String it parses it to ReqParams and then
  // 2) calls the appropriate server side f, which returns Response then
  // 3) encodes Response into JSON


  def g[V<:View,ReqLocal](implicit serverSideFun: ServerSideFun[V] with ViewParams[V]):serverSideFun.ResponseType ={



    ???
  }
}