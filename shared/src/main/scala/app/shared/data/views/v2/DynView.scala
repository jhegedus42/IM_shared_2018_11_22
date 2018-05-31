package app.shared.data.views.v2

// ViewReqDyn
// ViewRespDyn

object DynView {

  case class ViewRequestDyn(viewName:ViewName,payloadAsJSON:String)
  case class ViewResponseDyn(viewName:ViewName,payloadAsJSON:String)
  //  def viewPayloadToDyn[V<:View]()
//  def respToDyn()
}


// ez miben kulonbozik a JSON-tol amit atkuldunk a halon ?
// semmiben, ez csak egy wrapper a korul, hogy tudjuk, hogy Req v. Resp-rol
// van szo
