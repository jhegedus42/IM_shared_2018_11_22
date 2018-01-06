package app.client.ui.pages.lineList

import app.client.cache.wrapper.ReadAndWriteRequestQue
import app.client.ui.pages.{LineList, Props2Wrapped}
import app.client.ui.pages.Types.Wrapped_CompConstr
import app.client.ui.pages.main.root_children.materialUI_children.Pages.Page
import japgolly.scalajs.react.ReactElement
import japgolly.scalajs.react.extra.router.RouterCtl

/**
  * Created by joco on 06/01/2018.
  */
object LineListWrapping {

  type LineListProp = Unit

  val que: ReadAndWriteRequestQue = new ReadAndWriteRequestQue()

  val wLL: Wrapped_CompConstr[LineList.type, LineListProp] =
    que.wrapper.wrapRootPage[LineList.type, LineListProp]( LineList_ReactComp.LineListCompBuilder )

  val mk_wLL: ( RouterCtl[Page] ) => ReactElement = (r: RouterCtl[Page]) => wLL(Props2Wrapped((), r))


}
