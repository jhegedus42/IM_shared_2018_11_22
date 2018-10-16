package app.client.ui.pages.lineList

import app.client.cache.wrapper.CacheRoot
import app.client.ui.pages.{LineList_Wrappable_RootReactComp_PhantomType, PropsOfInnerComp}
import app.client.ui.pages.Types.WrappedCompConstr
import app.client.ui.pages.main.root_children.materialUI_children.Pages.Page
import japgolly.scalajs.react.ReactElement
import japgolly.scalajs.react.extra.router.RouterCtl

/**
  * Created by joco on 06/01/2018.
  * 
  */
object LineListWrapping {

  type LineListProp = Unit

  val que: CacheRoot = new CacheRoot()

  val wLL: WrappedCompConstr[LineList_Wrappable_RootReactComp_PhantomType.type, LineListProp] =
    que.wrapper.wrapRootPage[LineList_Wrappable_RootReactComp_PhantomType.type, LineListProp](LineList_ReactComp.LineListCompBuilder)

  val mk_wLL: ( RouterCtl[Page] ) => ReactElement = (r: RouterCtl[Page]) => wLL(PropsOfInnerComp((), r))


}
