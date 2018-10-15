package app.client.ui.pages.lineDetail

import app.client.cache.wrapper.CacheRoot
import app.client.ui.pages.LineDetail_RootReactCompType$
import app.client.ui.pages.Types.OuterCompConstr

/**
  * Created by joco on 06/01/2018.
  */
object LineDetailWrapping {
  val que = new CacheRoot()

  val wrapped: OuterCompConstr[LineDetail_RootReactCompType$.type, LineDetail_ReactComp.Prop] =
    que.wrapper.wrapRootPage[LineDetail_RootReactCompType$.type, LineDetail_ReactComp.Prop](
      LineDetail_ReactComp.lineDetailConstructor
    )

}
