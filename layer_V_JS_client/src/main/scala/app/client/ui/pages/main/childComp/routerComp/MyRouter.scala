package app.client.ui.pages.main.childComp.routerComp

import app.client.entityCache.entityCacheV1.types.componentProperties.Props_Navigator_To_Depth1CompConstr
import app.client.entityCache.entityCacheV1.{CacheState, RootReactCompConstr_Enhancer}
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.{LineDetail_Page, LineList_Page, LineListsOfUser, URL}
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator_depth1Components.lineDetail.LineDetail_ReactComp.Prop
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator_depth1Components.lineDetail.LineDetailWrapping
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator_depth1Components.lineList.LineListWrapping
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator_depth1Components.listOfLineLists.UserLineListsWrapping
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.navigatorPrivate.NavigatorFacade
import app.shared.data.ref.Ref
import app.shared.data.ref.uuid.UUID
import app.testHelpersShared.data.{TestEntities, TestEntitiesForStateThree}
import japgolly.scalajs.react.extra.router.{BaseUrl, Redirect, Router, RouterConfig, RouterConfigDsl, RouterCtl}
import japgolly.scalajs.react.{ReactComponentU, ReactElement, TopNode}

object MyRouter {

  private val notSpecUUID = java.util.UUID.fromString( TestEntities.theUUIDofTheLine )

  val reactCompWrapper: RootReactCompConstr_Enhancer = RootReactCompConstr_Enhancer.wrapper

  private[this] val navs: Map[String, URL] = Map(
    "User Line List" -> LineListsOfUser( UUID( TestEntitiesForStateThree.user1uuid ) ),
    "Line List" -> LineList_Page(),
    "Line Detail " -> LineDetail_Page( notSpecUUID )
                                                )

  private[this] def routerConfig(): RouterConfig[URL] =
    RouterConfigDsl[URL].buildConfig {
      dsl: RouterConfigDsl[URL] =>
        val dr_lineDetail: dsl.Rule = {

          val lineDetailCompCreatorForDynRenderR: (
              LineDetail_Page,
              RouterCtl[URL]
          ) => ReactComponentU[Props_Navigator_To_Depth1CompConstr[Prop], CacheState, _, TopNode] = {
            ( x: LineDetail_Page, r: RouterCtl[URL] ) =>
              LineDetailWrapping( reactCompWrapper ).constructor_used_by_the_parent_component(
                Props_Navigator_To_Depth1CompConstr( Ref.makeWithUUID( x.idOfLine ), r )
              )
          }

          {
            import dsl._
            dsl.dynamicRouteCT( "#item" / dsl.uuid.caseClass[LineDetail_Page] ) ~> dsl.dynRenderR(
              lineDetailCompCreatorForDynRenderR
            )
          }
        }

        val dr_userlinelist: dsl.Rule = {

          val ullw = UserLineListsWrapping( reactCompWrapper )

          val g: ( LineListsOfUser, RouterCtl[URL] ) => ReactElement =
            ( u: LineListsOfUser, r: RouterCtl[URL] ) =>
              ullw.wrapped_CC(
                Props_Navigator_To_Depth1CompConstr( Ref.makeWithUUID( u.id_ofUser ), r )
            )

          {
            import dsl._
            dynamicRouteCT( "#user" / uuid.caseClass[LineListsOfUser] ) ~> dynRenderR( g )

          }
        }

        import dsl._

        val sr_lineList = {
          val llw = LineListWrapping( reactCompWrapper )
          dsl.staticRoute( "#im", LineList_Page() ) ~> dsl.renderR( llw.mk_wLL )
        }

        val config: RouterConfig[URL] = {
          (trimSlashes
            | dr_userlinelist
            | sr_lineList
            | dr_lineDetail)
            .notFound( redirectToPage( LineList_Page() )( Redirect.Replace ) )
            .renderWith(
                         NavigatorFacade
                         .getNavigatorCompConstr(navs)(_, _)
            )
        }

        config
    }

  private[this] val baseUrl: BaseUrl = BaseUrl.fromWindowOrigin_/

  lazy val routerConstructor: Router[URL] =
    Router( baseUrl, routerConfig().logToConsole )

}
