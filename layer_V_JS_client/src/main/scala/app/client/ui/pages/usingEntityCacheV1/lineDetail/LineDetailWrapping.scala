package app.client.ui.pages.usingEntityCacheV1.lineDetail

import app.client.ui.pages.usingEntityCacheV1.lineDetail.LineDetail_ReactComp.lineDetailConstructor
import app.client.entityCache.entityCacheV1.RootReactCompConstr_Enhancer
import app.client.entityCache.entityCacheV1.types.RootPageConstructorTypes.CacheInjectorCompConstructor
import app.client.entityCache.entityCacheV1.types.Vanilla_RootReactComponent_PhantomTypes.LineDetail_Vanilla_RootReactComp_PhantomType

/**
  * Created by joco on 06/01/2018.
  */
case class LineDetailWrapping(comp_constructor_enhancer: RootReactCompConstr_Enhancer ) {
//  val que = new CacheRoot()

  val constructor_used_by_the_parent_component
    : CacheInjectorCompConstructor[LineDetail_Vanilla_RootReactComp_PhantomType.type,
                                               LineDetail_ReactComp.Prop] =
    comp_constructor_enhancer.createCacheInjectorCompConstructor[
      LineDetail_Vanilla_RootReactComp_PhantomType.type,
      LineDetail_ReactComp.Prop
    ](
      lineDetailConstructor
    )

}
