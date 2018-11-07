package app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator.lineDetail

import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator.lineDetail.LineDetail_ReactComp.lineDetailConstructor
import app.client.entityCache.entityCacheV1.RootReactCompConstr_Enhancer
import app.client.entityCache.entityCacheV1.types.RootPageConstructorTypes.Depth1CompConstr

/**
  * Created by joco on 06/01/2018.
  */
case class LineDetailWrapping(comp_constructor_enhancer: RootReactCompConstr_Enhancer ) {
//  val que = new CacheRoot()

  val constructor_used_by_the_parent_component
    : Depth1CompConstr[LineDetail_Page,
                                               LineDetail_ReactComp.Prop] =
    comp_constructor_enhancer.create_Depth1CompConstr_by_wrapping_Depth2CompConstructor[
      LineDetail_Page,
      LineDetail_ReactComp.Prop
    ](
      lineDetailConstructor
    )

}
