package app.shared.data.views.view_architecture_design_v3.shared

/**
  * Created by joco on 02/06/2018.
  */
trait ViewParams[V <: View]{
  type ResponseType <:ViewResponse[V]
  type RequestParametersType <: ViewReqParams[V]
}

object ViewParams {





}