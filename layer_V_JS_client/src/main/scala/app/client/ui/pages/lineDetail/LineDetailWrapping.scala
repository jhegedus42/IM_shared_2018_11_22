package app.client.ui.pages.lineDetail

import app.client.cache.wrapper.CacheRoot
import app.client.ui.types.RootPageConstructorTypes.WrappedRootPageCompConstr
import app.client.ui.types.Vanilla_RootReactComponent_PhantomTypes.LineDetail_Vanilla_RootReactComp_PhantomType
import app.client.ui.pages.lineDetail.LineDetail_ReactComp.lineDetailConstructor

/**
  * Created by joco on 06/01/2018.
  */
object LineDetailWrapping {
  val que = new CacheRoot()

  val wrapped: WrappedRootPageCompConstr[LineDetail_Vanilla_RootReactComp_PhantomType.type, LineDetail_ReactComp.Prop] =
    que.wrapper.createWrappedRootPageCompConstructor[LineDetail_Vanilla_RootReactComp_PhantomType.type, LineDetail_ReactComp.Prop](
                                                                                                                                    lineDetailConstructor
                                                                                                                                  )

}
