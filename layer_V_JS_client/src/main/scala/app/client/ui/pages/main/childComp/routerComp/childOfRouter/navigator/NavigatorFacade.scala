package app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator

import app.client.ui.generalReactComponents.ViewRelatedUtils
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.URL
import chandu0101.scalajs.react.components.materialui._
import japgolly.scalajs.react.ReactComponentC.ReqProps
import japgolly.scalajs.react.extra.Reusability
import japgolly.scalajs.react.extra.router._
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, _}
import org.scalajs.dom.html.Div

import scala.scalajs.js

package navigatorPrivate {

  import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.navigatorPrivate.NavigatorBackendHolder.{
    NavigatorBackend,
    NavigatorProps,
    NavigatorState
  }

  private[navigatorPrivate] object NavigatorBackendHolder {

    case class NavigatorState(drawerOpen: Boolean )

    case class NavigatorProps[URL_TP <: URL](
        routerCtl:  RouterCtl[URL_TP],
        resolution: Resolution[URL_TP],
        page:       URL_TP,
        navs:       Map[String, URL_TP],
        title:      String)

    class NavigatorBackend[URL_TP <: URL](scope: BackendScope[NavigatorProps[URL_TP], NavigatorState] ) {

      private val toggleDrawerOpen: Callback = {
        println( "callback running" );
        Callback.info( s"opening drawer" ) >>
          scope.modState( s => s.copy( drawerOpen = !s.drawerOpen ) )
      }

      private val onRequestChange: ( Boolean, String ) => Callback =
        ( open, reason ) =>
          Callback.info( s"onRequestChange: open: $open, reason: $reason" ) >>
            scope.modState( s => s.copy( drawerOpen = !s.drawerOpen ) )

      def render(p: NavigatorProps[URL_TP], s: NavigatorState ): ReactTagOf[Div] = {
        println( "render navigation" );
        System.out.flush();
        <.div(
          MuiAppBar(
            title = p.title: ReactNode,
            onLeftIconButtonTouchTap = {
              println( "on left icon button touch tap" )
              ViewRelatedUtils.touch( toggleDrawerOpen )
            },
            showMenuIconButton = true,
            style = js.Dynamic.literal(
              "position" -> "fixed",
              "top" -> "0px"
            )
          )(),
          MuiDrawer(
            onRequestChange = onRequestChange, //Toggle open state
            docked          = false,
            open            = s.drawerOpen
          )(
            <.div(
              ^.backgroundColor := "#757575",
              ^.color := "rgb(255, 255, 255)",
              ^.height := "64px"
            ),
            MuiMenu()(
              p.navs.toIndexedSeq.sortBy( _._1 ).map {
                case ( name, page ) =>
                  MuiMenuItem(
                    key           = name,
                    primaryText   = name: ReactNode,
                    checked       = p.page == page,
                    insetChildren = p.page != page, //Allow space for icon/checkmark when it's not displayed
                    onTouchTap    = ViewRelatedUtils.touch( p.routerCtl.set( page ) >> toggleDrawerOpen ),
                    style = js.Dynamic.literal(
                      "cursor" -> "pointer",
                      "user-select" -> "none"
                    )
                  )()
              }
            )
          ),
          <.div(
            ^.paddingTop := "64px",
            p.resolution.render()
          )
        )
      }
    }

  }

  private[navigatorPrivate] object NavigatorCompConstrHolder {
    //Reusable if all fields are equal except routerCtl, where we use its own reusability
    private implicit def navPropsReuse[P <: URL]: Reusability[NavigatorProps[P]] = Reusability.fn {
      case ( a, b ) if a eq b => true // First because most common case and fastest
      case ( a, b )
          if a.page == b.page && a.navs == b.navs && a.title == b.title && a.resolution == b.resolution =>
        RouterCtl.reusability[P].test( a.routerCtl, b.routerCtl )
      case _ => false
    }

    //Just make the component constructor - props to be supplied later to make a component
    private def apply[
        NavigatorChildDescriptor <: URL
      ]: ReqProps[NavigatorProps[NavigatorChildDescriptor], NavigatorState, NavigatorBackend[
      NavigatorChildDescriptor
    ], TopNode] =
      ReactComponentB[NavigatorProps[NavigatorChildDescriptor]]( "Nav" )
        .initialState( NavigatorState( false ) )
        .backend( new NavigatorBackend[NavigatorChildDescriptor]( _ ) )
        .render( s => s.backend.render( s.props, s.state ) )
        .build

    val navigatorCompConstr: ReqProps[NavigatorProps[
      URL
    ], NavigatorState, NavigatorBackend[
      URL
    ], TopNode] =
      apply[URL]

  }

  object NavigatorFacade {

    type NavigatorCompConstr =
      ReactComponentU[NavigatorProps[URL], NavigatorState, NavigatorBackend[URL], TopNode]

    def getNavigatorCompConstr( navs: Map[String, URL] )(ctl:  RouterCtl[URL],
        r:    Resolution[URL]
      ): NavigatorCompConstr = {
      val title = "Joco App"
      val np: NavigatorProps[URL] = NavigatorProps( ctl, r, r.page, navs, title )
      NavigatorCompConstrHolder.navigatorCompConstr( np )
    }

  }

}
