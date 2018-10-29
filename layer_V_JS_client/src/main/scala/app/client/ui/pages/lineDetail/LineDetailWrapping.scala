package app.client.ui.pages.lineDetail

import app.client.wrapper.types.RootPageConstructorTypes.WrappedRootPageCompConstr
import app.client.wrapper.types.Vanilla_RootReactComponent_PhantomTypes.LineDetail_Vanilla_RootReactComp_PhantomType
import app.client.ui.pages.lineDetail.LineDetail_ReactComp.lineDetailConstructor
import app.client.wrapper.cache.CacheRoot

/**
  * Created by joco on 06/01/2018.
  */
case class LineDetailWrapping(que:CacheRoot) {
//  val que = new CacheRoot()

  val wrapped: WrappedRootPageCompConstr[LineDetail_Vanilla_RootReactComp_PhantomType.type, LineDetail_ReactComp.Prop] =
    que.wrapper.createWrappedRootPageCompConstructor[LineDetail_Vanilla_RootReactComp_PhantomType.type, LineDetail_ReactComp.Prop](
                                                                                                                                    lineDetailConstructor
                                                                                                                                  )

}
