package app.client.ui.pages.main.childComp.routerComp

import app.client.entityCache.entityCacheV1.types.componentProperties.PropsGivenByTheRouter_To_Depth1Component
import app.client.entityCache.entityCacheV1.{CacheState, RootReactCompConstr_Enhancer}
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.Navigator
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.PossibleChildOfNavigator
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator.lineDetail.LineDetail_ReactComp.Prop
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator.lineDetail.{LineDetailWrapping, LineDetail_Page}
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator.lineList.{LineListWrapping, LineList_Page}
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator.listOfLineLists.{ListOfLineListsOfAGivenUser_Page, UserLineListsWrapping}
import app.shared.data.ref.Ref
import app.shared.data.ref.uuid.UUID
import app.testHelpersShared.data.{TestEntities, TestEntitiesForStateThree}
import japgolly.scalajs.react.extra.router.{BaseUrl, Redirect, Router, RouterConfig, RouterConfigDsl, RouterCtl}
import japgolly.scalajs.react.{ReactComponentU, ReactElement, TopNode}

object MyRouter {

  private val notSpecUUID = java.util.UUID.fromString( TestEntities.theUUIDofTheLine )

  private[this] val navs: Map[String, PossibleChildOfNavigator] = Map(
    "User Line List" -> ListOfLineListsOfAGivenUser_Page( UUID( TestEntitiesForStateThree.user1uuid ) ),
    "Line List" -> LineList_Page(),
    "Line Detail " -> LineDetail_Page( notSpecUUID )
  )

  private[this] def routerConfig(): RouterConfig[PossibleChildOfNavigator] =
    RouterConfigDsl[PossibleChildOfNavigator].buildConfig {
      dsl: RouterConfigDsl[PossibleChildOfNavigator] =>
        import dsl._

        val reactCompWrapper: RootReactCompConstr_Enhancer = RootReactCompConstr_Enhancer.wrapper

        val dr_lineDetail = {
          val ldw: LineDetailWrapping = LineDetailWrapping( reactCompWrapper )

          val lineDetailCompCreatorForDynRenderR: (
              LineDetail_Page,
              RouterCtl[PossibleChildOfNavigator]
          ) => ReactComponentU[PropsGivenByTheRouter_To_Depth1Component[Prop], CacheState, _, TopNode] = {
            ( x: LineDetail_Page, r: RouterCtl[PossibleChildOfNavigator] ) =>
              ldw.constructor_used_by_the_parent_component(
                PropsGivenByTheRouter_To_Depth1Component( Ref.makeWithUUID( x.idOfLine ), r )

                // KERDES ^^^ ide miert nem kell STATE ???
                // KI AZ AKI A STATE-ET BUZERALJA ???
                // mi a fasznak kell state ?
              )
          }

          dynamicRouteCT( "#item" / uuid.caseClass[LineDetail_Page] ) ~> dynRenderR(
            lineDetailCompCreatorForDynRenderR
          )
        }

        val dr_userlinelist: dsl.Rule = {

          val ullw = UserLineListsWrapping( reactCompWrapper )

          val g: ( ListOfLineListsOfAGivenUser_Page, RouterCtl[PossibleChildOfNavigator] ) => ReactElement =
            ( u: ListOfLineListsOfAGivenUser_Page, r: RouterCtl[PossibleChildOfNavigator] ) =>
              ullw.wrapped_CC(
                PropsGivenByTheRouter_To_Depth1Component( Ref.makeWithUUID( u.id_ofUser ), r )
            )

          dynamicRouteCT( "#user" / uuid.caseClass[ListOfLineListsOfAGivenUser_Page] ) ~> dynRenderR( g )
        }

        val sr_lineList = {
          val llw = LineListWrapping( reactCompWrapper )
          staticRoute( "#im", LineList_Page() ) ~> renderR( llw.mk_wLL )
        }

        val config: RouterConfig[PossibleChildOfNavigator] = {
          (trimSlashes
           | dr_userlinelist
           | sr_lineList
           | dr_lineDetail)
            .notFound( redirectToPage( LineList_Page() )( Redirect.Replace ) )
            .renderWith(
                         Navigator
                         .layout( navs )( _, _ )
                       )
        }

        config
    }

  private[this] val baseUrl: BaseUrl = BaseUrl.fromWindowOrigin_/

  lazy val routerConstructor: Router[PossibleChildOfNavigator] =
    Router( baseUrl, routerConfig().logToConsole )

}
