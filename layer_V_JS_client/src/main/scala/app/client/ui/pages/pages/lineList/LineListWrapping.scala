package app.client.ui.pages.pages.lineList

import app.client.ui.pages.main.root_children.materialUI_children.Pages.Page
import app.client.entityCache.entityCacheV1.RootReactCompConstr_Enhancer
import app.client.entityCache.entityCacheV1.types.RootPageConstructorTypes.Depth1CompConstr
import app.client.entityCache.entityCacheV1.types.Vanilla_RootReactComponent_PhantomTypes.LineList_Vanilla_RootReactComp_PhantomType
import app.client.entityCache.entityCacheV1.types.componentProperties.PropsGivenByTheRouter_To_Depth1Component
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

  val wLL: Depth1CompConstr[LineList_Vanilla_RootReactComp_PhantomType.type, LineListProp] =
    wrapper.depth1CompConstr[
      LineList_Vanilla_RootReactComp_PhantomType.type, LineListProp](LineList_ReactComp.LineListCompBuilder)

  val mk_wLL: ( RouterCtl[Page] ) => ReactElement = (r: RouterCtl[Page]) => wLL(PropsGivenByTheRouter_To_Depth1Component((), r))


}
