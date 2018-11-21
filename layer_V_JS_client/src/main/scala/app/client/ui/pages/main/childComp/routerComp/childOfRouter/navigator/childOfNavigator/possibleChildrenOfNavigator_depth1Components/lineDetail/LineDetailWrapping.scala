package app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator_depth1Components.lineDetail

import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator_depth1Components.lineDetail.LineDetail_CompConstr_Holder.lineDetail_Depth2CompConstructor
import app.client.entityCache.entityCacheV1.RootReactCompConstr_Enhancer
import app.client.entityCache.entityCacheV1.types.RootPageConstructorTypes.Depth1CompConstr_Alias
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.LineDetail_URL

/**
  * Created by joco on 06/01/2018.
  */
case class LineDetailWrapping(enhancer: RootReactCompConstr_Enhancer ) {
//  val que = new CacheRoot()

  val constructor_used_by_the_parent_component: Depth1CompConstr_Alias[LineDetail_URL, LineDetail_CompConstr_Holder.Depth1CompProps_Alias] =
    enhancer.create_Depth1CompConstr_by_wrapping_Depth2CompConstructor[ LineDetail_URL, LineDetail_CompConstr_Holder.Depth1CompProps_Alias ](
       lineDetail_Depth2CompConstructor
                                                                                                                                            )

}
