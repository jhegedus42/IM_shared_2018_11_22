package app.client.ui.pages.main.childComp.routerComp

import app.client.entityCache.entityCacheV1.RootReactCompConstr_Enhancer
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator_depth1Components.lineDetail.LineDetail_CompConstr_Holder
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator_depth1Components.lineList.LineListWrapping
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator_depth1Components.listOfLineLists.UserLineListsCompConstr_Holder
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.{LineDetail_URL, LineList_Page, LineListsOfUser_URL, URL_STr}
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.navigatorPrivate.NavigatorFacade
import app.shared.data.ref.uuid.UUID
import app.testHelpersShared.data.{TestEntities, TestEntitiesForStateThree}
import japgolly.scalajs.react.extra.router.{BaseUrl, Redirect, Router, RouterConfig, RouterConfigDsl}

object MyRouter {

  private val notSpecUUID = java.util.UUID.fromString( TestEntities.theUUIDofTheLine )

  val reactCompWrapper: RootReactCompConstr_Enhancer = RootReactCompConstr_Enhancer.wrapper

  private[this] val navs: Map[String, URL_STr] = Map(
    "User Line List" -> LineListsOfUser_URL( UUID( TestEntitiesForStateThree.user1uuid ) ),
    "Line List" -> LineList_Page(),
    "Line Detail " -> LineDetail_URL( notSpecUUID )
  )

  private[this] def routerConfig(): RouterConfig[URL_STr] =
    RouterConfigDsl[URL_STr].buildConfig {
      dsl: RouterConfigDsl[URL_STr] =>

        import dsl._

        val dr_lineDetail: dsl.Rule =
          dsl.dynamicRouteCT( "#item" / dsl.uuid.caseClass[LineDetail_URL] ) ~>
            dsl.dynRenderR(
              LineDetail_CompConstr_Holder.getCompConstr_For_dynRenderR_In_Router( reactCompWrapper ) )

        val dr_userlinelist: dsl.Rule =
          dynamicRouteCT( "#user" / uuid.caseClass[LineListsOfUser_URL] ) ~>
            dynRenderR( UserLineListsCompConstr_Holder.getFunctionNeededForDynRenderR( reactCompWrapper ) )

        import dsl._

        val sr_lineList: dsl.Rule =
          dsl.staticRoute( "#im", LineList_Page() ) ~>
            dsl.renderR( LineListWrapping( reactCompWrapper ).mk_wLL )

        val config: RouterConfig[URL_STr] = {
          (dsl.trimSlashes
            | dr_userlinelist
            | sr_lineList
            | dr_lineDetail)
            .notFound( dsl.redirectToPage( LineList_Page() )( Redirect.Replace ) )
            .renderWith(
              NavigatorFacade.getNavigatorCompConstr( navs )( _, _ )
            )
        }

        config
    }

  private[this] val baseUrl: BaseUrl = BaseUrl.fromWindowOrigin_/

  lazy val routerConstructor: Router[URL_STr] =
    Router( baseUrl, routerConfig().logToConsole )

}
