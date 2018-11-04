package app.client.ui.pages.main.root_children.materialUI_children

import app.client.entityCache.entityCacheV1.types.componentProperties.PropsGivenByTheRouter_To_Depth1Component
import app.client.ui.pages.usingEntityCacheV1.lineDetail.LineDetailWrapping
import app.client.ui.pages.usingEntityCacheV1.lineList.LineListWrapping
import app.client.ui.pages.usingEntityCacheV1.listOfLineLists.UserLineListsWrapping
import app.client.ui.pages.main.root_children.MaterialUI_Main_ReactComponent
import app.client.ui.pages.main.root_children.materialUI_children.Pages.{LineDetailPage, LineListPage, UserLineListPage}
import app.client.entityCache.entityCacheV1.{CurrentStateOfCache, RootReactCompConstr_Enhancer}
import app.client.ui.pages.usingEntityCacheV1.lineDetail.LineDetail_ReactComp.Prop
import app.shared.data.ref.Ref
import app.shared.data.ref.uuid.UUID
import app.testHelpersShared.data.{TestEntities, TestEntitiesForStateThree}
import japgolly.scalajs.react.{ReactComponentU, ReactElement, TopNode}
import japgolly.scalajs.react.extra.router.RouterCtl

object Pages {
  sealed trait Page
  case object LineListPage extends Page
  case object ReorderList extends Page
  case class LineDetailPage(id:        java.util.UUID ) extends Page
  case class UserLineListPage(id_user: java.util.UUID ) extends Page
}

import japgolly.scalajs.react.extra.router.{BaseUrl, Redirect, Router, RouterConfig, RouterConfigDsl}

object RouterComp {

  val notSpecUUID = java.util.UUID.fromString( TestEntities.theUUIDofTheLine )

  import Pages.Page

  private[this] val navs: Map[String, Page] = Map(
    "User Line List" -> UserLineListPage( UUID( TestEntitiesForStateThree.user1uuid ) ),
    "Line List" -> LineListPage,
    "Line Detail " -> LineDetailPage( notSpecUUID )
  )

  private[this] def routerConfig(): RouterConfig[Page] =
    RouterConfigDsl[Page].buildConfig {
      (dsl: RouterConfigDsl[Page]) =>
        import dsl._

        val reactCompWrapper: RootReactCompConstr_Enhancer = RootReactCompConstr_Enhancer.wrapper

        val dr_lineDetail = {
          val ldw: LineDetailWrapping = LineDetailWrapping( reactCompWrapper )

          val lineDetailCompCreatorForDynRenderR
            : ( LineDetailPage, RouterCtl[Page] ) => ReactComponentU[PropsGivenByTheRouter_To_Depth1Component[Prop],
                                                                     CurrentStateOfCache,
                                                                     _,
                                                                     TopNode] = {
            ( x: LineDetailPage, r: RouterCtl[Page] ) =>
              ldw.constructor_used_by_the_parent_component(
                                                            PropsGivenByTheRouter_To_Depth1Component(Ref.makeWithUUID(x.id), r)

                                                            // KERDES ^^^ ide miert nem kell STATE ???
                                                            // KI AZ AKI A STATE-ET BUZERALJA ???
                                                            // mi a fasznak kell state ?


              )
          }

          dynamicRouteCT( "#item" / uuid.caseClass[LineDetailPage] ) ~> dynRenderR(
            lineDetailCompCreatorForDynRenderR
          )
        }

        val dr_userlinelist: dsl.Rule = {

          val ullw = UserLineListsWrapping( reactCompWrapper )

          val g: ( UserLineListPage, RouterCtl[Page] ) => ReactElement =
            ( u: UserLineListPage, r: RouterCtl[Page] ) =>
              ullw.wrapped_CC(PropsGivenByTheRouter_To_Depth1Component(Ref.makeWithUUID(u.id_user), r))

          dynamicRouteCT( "#user" / uuid.caseClass[UserLineListPage] ) ~> dynRenderR( g )
        }

        val sr_lineList = {
          val llw = LineListWrapping( reactCompWrapper )
          staticRoute( "#im", LineListPage ) ~> renderR( llw.mk_wLL )
        }

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

  private[this] val baseUrl: BaseUrl = BaseUrl.fromWindowOrigin_/

  def constructor() = Router( baseUrl, routerConfig().logToConsole )

}
