//package app.client.ui.pages.lineList
//
//import app.client.cache.entityCache.EntityCacheMap
//import app.client.ui.pages.main.root_children.materialUI_children.Pages.{LineDetailPage, Page}
//import app.client.ui.generalReactComponents.wrapped_JS_Components.react_sortable_hoc.{SortableContainer, SortableElement, SortableView}
//import app.shared.data.ref.RefVal
//import app.shared.data.utils.model.LineOld
//import japgolly.scalajs.react.ReactComponentC.ReqProps
//import japgolly.scalajs.react.extra.router.RouterCtl
//import japgolly.scalajs.react.vdom.prefix_<^._
//import japgolly.scalajs.react.{ReactComponentB, _}
//
//
//object ReorderList {
//  case class LLRC_Props(ctl: RouterCtl[Page], c: EntityCacheMap)
//  val itemView: ReqProps[(RefVal[LineOld], LLRC_Props), Unit, Unit, TopNode] =
//    ReactComponentB[(RefVal[LineOld], LLRC_Props)]("liView")
//      .render(d => {
//        <.div(
//          ^.className := "react-sortable-item",
//          SortableView.handle,
//          <.div(<.img(^.src := d.props._1.v.getImgSrc.url,
//                      ^.height := 60,
//                      ^.margin := "auto",
//                      ^.display := "block"),
//                ^.width := 100),
//          d.props._2.ctl.link(LineDetailPage(d.props._1.r.uuid))(
//            s"${d.props._1.v.pl.text}")
//        )
//      })
//      .build
//
//  val sortableItem: (SortableElement.Props) => (
//      (RefVal[LineOld], LLRC_Props)) => ReactComponentU_ =
//    SortableElement.wrap(itemView)
//
//  val listView
//    : ReqProps[(List[RefVal[LineOld]], LLRC_Props), Unit, Unit, TopNode] =
//    ReactComponentB[(List[RefVal[LineOld]], LLRC_Props)]("listView")
//      .render(d => {
//        <.div(
//          ^.className := "react-sortable-list",
//          d.props._1.zipWithIndex.map {
//            case (value: RefVal[LineOld], index) =>
//              sortableItem(SortableElement.Props(index = index))(
//                (value, d.props._2))
//          }
//        )
//      })
//      .build
//
//  val sortableList: (SortableContainer.Props) => (
//      (List[RefVal[LineOld]], LLRC_Props)) => ReactComponentU_ =
//    SortableContainer.wrap(listView)
//}
