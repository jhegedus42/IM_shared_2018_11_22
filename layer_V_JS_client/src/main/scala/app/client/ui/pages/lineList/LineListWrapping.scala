package app.client.ui.pages.lineList

import app.client.wrapper.types.RootPageConstructorTypes.WrappedRootPageCompConstr
import app.client.wrapper.types.Vanilla_RootReactComponent_PhantomTypes.LineList_Vanilla_RootReactComp_PhantomType
import app.client.ui.pages.main.root_children.materialUI_children.Pages.Page
import app.client.wrapper.cache.CacheRoot
import app.client.wrapper.types.PropsOfVanillaComp
import japgolly.scalajs.react.ReactElement
import japgolly.scalajs.react.extra.router.RouterCtl

/**
  * Created by joco on 06/01/2018.
  * 
  */
case class LineListWrapping(que:CacheRoot) {

  type LineListProp = Unit

//  val que: CacheRoot = new CacheRoot()

  val wLL: WrappedRootPageCompConstr[LineList_Vanilla_RootReactComp_PhantomType.type, LineListProp] =
    que.wrapper.createWrappedRootPageCompConstructor[
      LineList_Vanilla_RootReactComp_PhantomType.type, LineListProp](LineList_ReactComp.LineListCompBuilder)

  val mk_wLL: ( RouterCtl[Page] ) => ReactElement = (r: RouterCtl[Page]) => wLL(PropsOfVanillaComp((), r))


}
