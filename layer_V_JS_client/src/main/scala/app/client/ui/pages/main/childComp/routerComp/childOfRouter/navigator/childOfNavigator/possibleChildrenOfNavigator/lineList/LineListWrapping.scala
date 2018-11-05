package app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator.lineList

import app.client.entityCache.entityCacheV1.RootReactCompConstr_Enhancer
import app.client.entityCache.entityCacheV1.types.RootPageConstructorTypes.Depth1CompConstr
import app.client.entityCache.entityCacheV1.types.componentProperties.PropsGivenByTheRouter_To_Depth1Component
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.PossibleChildOfNavigator
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator.lineDetail.LineDetail_Page
import japgolly.scalajs.react.ReactElement
import japgolly.scalajs.react.extra.router.RouterCtl

/**
  * Created by joco on 06/01/2018.
  * 
  */
case class LineListWrapping(wrapper:
                            RootReactCompConstr_Enhancer) {

  type LineListProp = Unit

//  val que: CacheRoot = new CacheRoot()

  val wLL: Depth1CompConstr[LineDetail_Page, LineListProp] =
    wrapper.depth1CompConstr[
      LineDetail_Page, LineListProp](LineList_ReactComp.LineListCompBuilder)

  val mk_wLL: ( RouterCtl[PossibleChildOfNavigator] ) => ReactElement = (r: RouterCtl[PossibleChildOfNavigator]) => wLL(PropsGivenByTheRouter_To_Depth1Component((), r))


}
