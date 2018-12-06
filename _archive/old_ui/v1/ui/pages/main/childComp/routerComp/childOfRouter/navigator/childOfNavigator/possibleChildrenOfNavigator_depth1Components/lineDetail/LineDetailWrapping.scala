package app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator_depth1Components.lineDetail

import app.client.entityCache.entityCacheV1.D2toD1Transformer
import app.client.entityCache.entityCacheV1.types.RootPageConstructorTypes
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.LineDetail_URL
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator_depth1Components.lineDetail.LineDetail_CompConstr_Holder.{
  LineDetail_D1_Props,
  lineDetail_Depth2CompConstructor
}

/**
  * Created by joco on 06/01/2018.
  */
case class LineDetailWrapping(trD2toD1_provider: D2toD1Transformer ) {
//  val que = new CacheRoot()

  type D1Wrapper = RootPageConstructorTypes.Depth1CompConstrWrapper[LineDetail_URL, LineDetail_D1_Props]

  val constructor_used_by_the_parent_component: D1Wrapper =
    trD2toD1_provider.trD2toD1[
      LineDetail_URL,
      LineDetail_CompConstr_Holder.LineDetail_D1_Props
    ](
      lineDetail_Depth2CompConstructor
    )

}
