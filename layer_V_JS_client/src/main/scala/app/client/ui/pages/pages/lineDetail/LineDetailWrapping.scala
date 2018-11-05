package app.client.ui.pages.pages.lineDetail

import app.client.ui.pages.pages.lineDetail.LineDetail_ReactComp.lineDetailConstructor
import app.client.entityCache.entityCacheV1.RootReactCompConstr_Enhancer
import app.client.entityCache.entityCacheV1.types.RootPageConstructorTypes.Depth1CompConstr
import app.client.entityCache.entityCacheV1.types.Vanilla_RootReactComponent_PhantomTypes.LineDetail_Vanilla_RootReactComp_PhantomType

/**
  * Created by joco on 06/01/2018.
  */
case class LineDetailWrapping(comp_constructor_enhancer: RootReactCompConstr_Enhancer ) {
//  val que = new CacheRoot()

  val constructor_used_by_the_parent_component
    : Depth1CompConstr[LineDetail_Vanilla_RootReactComp_PhantomType.type,
                                               LineDetail_ReactComp.Prop] =
    comp_constructor_enhancer.depth1CompConstr[
      LineDetail_Vanilla_RootReactComp_PhantomType.type,
      LineDetail_ReactComp.Prop
    ](
      lineDetailConstructor
    )

}
