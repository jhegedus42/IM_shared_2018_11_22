package app.client.ui.pages.main.root_children.materialUI_children

import app.client.ui.pages.main.root_children.MaterialUI_Main_ReactComponent
import app.client.ui.pages.main.root_children.materialUI_children.Pages.{LineDetailPage, LineListPage}
import app.client.ui.pages.main.root_children.materialUI_children.router_children.wrappedComponents.{LineDetailWrapping, LineListWrapping}
import app.shared.model.ref.Ref
import app.testHelpersShared.data.TestEntities

object Pages {
  sealed trait Page
  case object LineListPage extends Page
  case object ReorderList extends Page
  case class LineDetailPage(id: java.util.UUID ) extends Page
}

import japgolly.scalajs.react.extra.router.{BaseUrl, Redirect, Router, RouterConfig, RouterConfigDsl}


object RouterComp {

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

        val config: RouterConfig[Page] = (trimSlashes
          | staticRoute( "#im", LineListPage ) ~> renderR( LineListWrapping.mk_wLL )
          | dr_lineDetail)
          .notFound( redirectToPage( LineListPage )( Redirect.Replace ) )
          .renderWith(
                       MaterialUI_Main_ReactComponent
                       .layout( navs )( _, _ )
          )
        config
    }

  val baseUrl = BaseUrl.fromWindowOrigin_/

  def constructor() = Router( baseUrl, routerConfig().logToConsole )

}
