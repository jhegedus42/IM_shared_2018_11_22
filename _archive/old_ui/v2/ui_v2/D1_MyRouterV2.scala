package app.client.ui_v2

import app.client.ui_v2.D1_MyRouterV2.URLs.{LineListPageURLRepr, UserIDDisplayer, URL_Repr}
//import app.client.ui_v2.D1_MyRouterV2.NavigatorFacade
import app.shared.data.ref.uuid.UUID
import app.testHelpersShared.data.TestEntitiesForStateThree

import japgolly.scalajs.react.ReactElement
import japgolly.scalajs.react.extra.router.{
  BaseUrl,
  Redirect,
  Router,
  RouterConfig,
  RouterConfigDsl,
  RouterCtl
}

object D1_MyRouterV2 {

  // ez fog majd egy kess-t csinalni es beinjektalja a D3 komponensekbe
  // ... vhogy ... majd megleglatjuk, hogy hogy ...

  object URLs {

    sealed trait URL_Repr // this is called Page in the router world

    case class LineListPageURLRepr() extends URL_Repr

    case class UserIDDisplayer(id_ofUser: java.util.UUID ) extends URL_Repr
  }

  private[this] val navs: Map[String, URL_Repr] = Map(
    "User Line List" -> UserIDDisplayer( UUID( TestEntitiesForStateThree.user1uuid ) ),
    "Line List" -> LineListPageURLRepr()
  )

  private[this] def routerConfig(): RouterConfig[URL_Repr] =
    RouterConfigDsl[URL_Repr].buildConfig {
      dsl: RouterConfigDsl[URL_Repr] =>
        import dsl._

        val g: ( UserIDDisplayer, RouterCtl[UserIDDisplayer] ) => ReactElement = ??? //
        // TODO 1 put Simplest ReactComp ^^^ here

//        def dynRenderR[P <: Page, A <% ReactElement](g: (P, RouterCtl[Page]) => A): P => Renderer = ...

        val dr_userlinelist: dsl.Rule =
          dynamicRouteCT( "#user" / uuid.caseClass[UserIDDisplayer] ) ~> dynRenderR( ??? )
        // TODO 2 use it UserIDDisplayer here ^^^^


        import dsl._

        val sr_lineList: dsl.Rule = dsl.staticRoute( "#im", LineListPageURLRepr() ) ~> dsl.renderR( ??? )

        val config: RouterConfig[URL_Repr] = {
          (dsl.trimSlashes
            | dr_userlinelist
            | sr_lineList)
            .notFound( dsl.redirectToPage( LineListPageURLRepr() )( Redirect.Replace ) )
            .renderWith(
              NavigatorFacade.getNavigatorCompConstr( navs )( _, _ )
            )
        }

        config
    }

  private[this] val baseUrl: BaseUrl = BaseUrl.fromWindowOrigin_/

  lazy val routerConstructor: Router[URL_Repr] =
    Router( baseUrl, routerConfig().logToConsole )

}
