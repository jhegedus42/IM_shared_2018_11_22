package app.shared.data.views.v3_view_architecture_design.shared.views.common


/**
  * Created by joco on 02/06/2018.
  */


trait ViewParams[V <: View]{
  type ResponseType <: ViewResponse[V]
  type RequestParametersType <: ViewReqParams[V]
}


object ViewParams {
  type Aux[ V1<:View, Parameters, Response] =
    ViewParams[V1] {
      type ResponseType = Response
      type RequestParametersType = Parameters
    }

}