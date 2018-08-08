package app.client.ui.pages.main.root_children.materialUI_children

import app.client.ui.pages.Props2Wrapped
import app.client.ui.pages.lineDetail.LineDetailWrapping
import app.client.ui.pages.lineList.LineListWrapping
import app.client.ui.pages.listOfLineLists.UserLineListsWrapping
import app.client.ui.pages.main.root_children.MaterialUI_Main_ReactComponent
import app.client.ui.pages.main.root_children.materialUI_children.Pages.{LineDetailPage, LineListPage, UserLineListPage}
import app.shared.data.model.UserLineList
import app.shared.data.ref.Ref
import app.shared.data.ref.uuid.UUID
import app.testHelpersShared.data.{TestEntities, TestEntitiesForStateThree}
import japgolly.scalajs.react.ReactElement
import japgolly.scalajs.react.extra.router.RouterCtl

object Pages {
  sealed trait Page
  case object LineListPage extends Page
  case object ReorderList extends Page
  case class LineDetailPage(id: java.util.UUID ) extends Page
  case class UserLineListPage(id_user: java.util.UUID ) extends Page
}

import japgolly.scalajs.react.extra.router.{BaseUrl, Redirect, Router, RouterConfig, RouterConfigDsl}

object RouterComp {

  val notSpecUUID = java.util.UUID.fromString( TestEntities.theUUIDofTheLine )

  import Pages.Page

  val navs: Map[String, Page] = Map(
    "User Line List" -> UserLineListPage(UUID(TestEntitiesForStateThree.user1uuid)),
    "Line List" -> LineListPage,
    "Line Detail " -> LineDetailPage( notSpecUUID )

  )

  def routerConfig(): RouterConfig[Page] =
    RouterConfigDsl[Page].buildConfig {
      (dsl: RouterConfigDsl[Page]) =>
        import dsl._

        val dr_lineDetail = {
          val g = {
            ( x: LineDetailPage, r: RouterCtl[Page] ) =>
              LineDetailWrapping.wrapped(Props2Wrapped(Ref.makeWithUUID(x.id), r))
          }
          dynamicRouteCT( "#item" / uuid.caseClass[LineDetailPage] ) ~> dynRenderR( g )
        }

        val dr_userlinelist={

          val g: (UserLineListPage,  RouterCtl[Page] ) => ReactElement =
            (u:UserLineListPage,r: RouterCtl[Page]) =>
              UserLineListsWrapping.wrapped_CC( Props2Wrapped(Ref.makeWithUUID(u.id_user), r ) )

          dynamicRouteCT( "#user" / uuid.caseClass[UserLineListPage]) ~> dynRenderR(g)
        }

        val sr_lineList = staticRoute( "#im", LineListPage ) ~> renderR( LineListWrapping.mk_wLL )

        val config: RouterConfig[Page] = (trimSlashes
          | dr_userlinelist
          | sr_lineList
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