package app.client.ui.pages.main

import app.client.cache.wrapper.{ReactCompWrapper, ReadAndWriteRequestQue}
import app.client.ui.pages.{LineDetail, LineList}
import app.client.ui.pages.lineDetail.LineDetail_ReactComp
import app.client.ui.pages.lineList.LineList_ReactComp
import app.client.ui.pages.main.Pages.{LineDetailPage, LineListPage}
import app.shared.model.ref.Ref
import app.testHelpersShared.data.TestEntities
import japgolly.scalajs.react.ReactElement

import scala.scalajs.js.|

object Pages {
  sealed trait Page
  case object LineListPage extends Page
  case object ReorderList extends Page
  case class LineDetailPage(id: java.util.UUID ) extends Page
}

import app.client.ui.pages.Types._

import japgolly.scalajs.react.extra.router.{
  BaseUrl,
  Redirect,
  Router,
  RouterConfig,
  RouterConfigDsl,
  RouterCtl
}

import Pages.Page

object LineListWrapping {

  type LineListProp = Unit

  val que: ReadAndWriteRequestQue = new ReadAndWriteRequestQue()

  val wLL: Wrapped_CompConstr[LineList.type, LineListProp] =
    que.wrapper.wrapRootPage[LineList.type, LineListProp]( LineList_ReactComp.LineListCompBuilder )

  val mk_wLL: ( RouterCtl[Page] ) => ReactElement = (r: RouterCtl[Page]) => wLL( ( (), r ) )


}

object LineDetailWrapping {
  val que = new ReadAndWriteRequestQue()

  val wrapped_lineDetail_compConstr: Wrapped_CompConstr[LineDetail.type, LineDetail_ReactComp.Prop] =
    que.wrapper.wrapRootPage[LineDetail.type, LineDetail_ReactComp.Prop](
      LineDetail_ReactComp.lineDetailConstructor
    )

}

object RouterComp {

  import app.client.ui.pages.lineList.LineList_ReactComp

  val notSpecUUID = java.util.UUID.fromString( TestEntities.theUUIDofTheLine )

  import Pages.Page

  val navs: Map[String, Page] = Map(
    "Line List" -> LineListPage,
    "Line Detail " -> LineDetailPage( notSpecUUID )
  )

  def routerConfig(): RouterConfig[Page] =
    RouterConfigDsl[Page].buildConfig {
      (dsl: RouterConfigDsl[Page]) =>
        import dsl._

        val dr_lineDetail = dynamicRouteCT( "#item" / uuid.caseClass[LineDetailPage] ) ~>
          dynRenderR( {
            ( x: LineDetailPage, r ) =>
              LineDetailWrapping.wrapped_lineDetail_compConstr( ( Ref.makeWithUUID( x.id ), r ) )
          } )

        import japgolly.scalajs.react.vdom.prefix_<^._

        val config: RouterConfig[Page] = (trimSlashes
          | staticRoute( "#im", LineListPage ) ~> renderR( LineListWrapping.mk_wLL )
          | dr_lineDetail)
          .notFound( redirectToPage( LineListPage )( Redirect.Replace ) )
          .renderWith(
            app.client.ui.pages.main.MaterialUI_Main_ReactComponent
              .layout( navs )( _, _ )
          )
        config
    }

  val baseUrl = BaseUrl.fromWindowOrigin_/

  def constructor() = Router( baseUrl, routerConfig().logToConsole )

}
