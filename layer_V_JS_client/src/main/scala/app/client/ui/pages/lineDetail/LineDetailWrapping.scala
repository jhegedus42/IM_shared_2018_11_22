package app.client.ui.pages.lineDetail

import app.client.ui.pages.lineDetail.LineDetail_ReactComp.lineDetailConstructor
import app.client.wrapper.ReactCompWrapper
import app.client.wrapper.types.RootPageConstructorTypes.WrappedRootPageCompConstr
import app.client.wrapper.types.Vanilla_RootReactComponent_PhantomTypes.LineDetail_Vanilla_RootReactComp_PhantomType

/**
  * Created by joco on 06/01/2018.
  */
case class LineDetailWrapping(wrapper:ReactCompWrapper) {
//  val que = new CacheRoot()

  val wrapped: WrappedRootPageCompConstr[LineDetail_Vanilla_RootReactComp_PhantomType.type, LineDetail_ReactComp.Prop] =
    wrapper.createWrappedRootPageCompConstructor[LineDetail_Vanilla_RootReactComp_PhantomType.type, LineDetail_ReactComp.Prop](
                                                                                                                                    lineDetailConstructor
                                                                                                                                  )

}
