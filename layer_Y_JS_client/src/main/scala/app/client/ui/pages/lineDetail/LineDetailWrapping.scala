package app.client.ui.pages.lineDetail

import app.client.cache.wrapper.CacheRoot
import app.client.ui.pages.LineDetail_Wrappable_RootReactComp_PhantomType
import app.client.ui.pages.Types.WrappedCompConstr

/**
  * Created by joco on 06/01/2018.
  */
object LineDetailWrapping {
  val que = new CacheRoot()

  val wrapped: WrappedCompConstr[LineDetail_Wrappable_RootReactComp_PhantomType.type, LineDetail_ReactComp.Prop] =
    que.wrapper.wrapRootPage[LineDetail_Wrappable_RootReactComp_PhantomType.type, LineDetail_ReactComp.Prop](
      LineDetail_ReactComp.lineDetailConstructor
                                                                                                            )

}
