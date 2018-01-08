package app.client.ui.pages.lineDetail

import app.client.cache.wrapper.CacheRoot
import app.client.ui.pages.LineDetailCompType
import app.client.ui.pages.Types.Wrapped_CompConstr

/**
  * Created by joco on 06/01/2018.
  */
object LineDetailWrapping {
  val que = new CacheRoot()

  val wrapped: Wrapped_CompConstr[LineDetailCompType.type, LineDetail_ReactComp.Prop] =
    que.wrapper.wrapRootPage[LineDetailCompType.type, LineDetail_ReactComp.Prop](LineDetail_ReactComp.lineDetailConstructor)

}
