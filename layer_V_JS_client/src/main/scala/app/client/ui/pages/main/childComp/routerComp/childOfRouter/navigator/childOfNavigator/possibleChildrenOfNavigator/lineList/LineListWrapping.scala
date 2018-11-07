package app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator.lineList

import app.client.entityCache.entityCacheV1.RootReactCompConstr_Enhancer
import app.client.entityCache.entityCacheV1.types.RootPageConstructorTypes.Depth1CompConstr
import app.client.entityCache.entityCacheV1.types.componentProperties.PropsGivenByTheRouter_To_Depth1Component
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.MainPage
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

  val wLL: Depth1CompConstr[LineList_Page, LineListProp] =
    wrapper.create_Depth1CompConstr_by_wrapping_Depth2CompConstructor[
      LineList_Page, Unit](LineList_ReactComp.LineListCompBuilder)

  val mk_wLL: ( RouterCtl[MainPage] ) => ReactElement = (r: RouterCtl[MainPage]) => wLL(PropsGivenByTheRouter_To_Depth1Component((), r))


}
