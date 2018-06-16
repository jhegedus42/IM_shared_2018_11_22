package app.shared.data.views.v3_view_architecture_design.server.stateAccess.viewQueries

import app.shared.data.views.v3_view_architecture_design.shared.views.View1_HolderObject.View1
import app.shared.data.views.v3_view_architecture_design.shared.views.View1_HolderObject.ReqParams_View1
import app.shared.data.views.v3_view_architecture_design.shared.views.View1_HolderObject.Response_View1
import app.shared.data.views.v3_view_architecture_design.shared.views.common.{View, ViewReqParams, ViewResponse}
import io.circe.{Decoder, Encoder}

/**
  * Created by joco on 03/06/2018.
  */
object ServerSideFunMultiParamTypeClass {

  implicit object InstanceForView1 extends ServerSideFunMultiParamTypeClass[View1,ReqParams_View1,Response_View1]{
    override def f(p: ReqParams_View1) = Response_View1("bla "+p.s)
  }


}

trait ServerSideFunMultiParamTypeClass[V<:View, Params<:ViewReqParams[V], Response<:ViewResponse[V]] {
  def f(p:Params):Response
}

object Test_ServerSideFunMultiParamTypeClass {

  // let's create a function endpointCreator, that creates a class that has a h() that uses g()
  // from below

  // let's create a function g
  // 1) to which when we pass a String it parses it to ReqParams and then
  // 2) calls the appropriate server side f, which returns Response then
  // 3) encodes Response into JSON

  def g[V<:View, Params<:ViewReqParams[V]:Decoder, Response<:ViewResponse[V]:Encoder](json:String)
       (implicit f:ServerSideFunMultiParamTypeClass[V,Params,Response]): String
    =
     {

      "bla"
     }

}