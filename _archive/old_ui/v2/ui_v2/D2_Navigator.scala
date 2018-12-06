package app.client.ui_v2

import app.client.ui_v2.D1_MyRouterV2.URLs.URL_Repr
import app.client.ui_v2.NavigatorBackendHolder.{NavigatorBackend, NavigatorProps, NavigatorState}
import app.client.ui_v2._utils.ViewRelatedUtils
import chandu0101.scalajs.react.components.materialui._
import japgolly.scalajs.react.ReactComponentC.ReqProps
import japgolly.scalajs.react.extra.Reusability
import japgolly.scalajs.react.extra.router._
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, _}
import org.scalajs.dom.html.Div

import scala.scalajs.js

  object NavigatorBackendHolder {

    case class NavigatorState(drawerOpen: Boolean )

    case class NavigatorProps[URL_TP <: URL_Repr](
        routerCtl:  RouterCtl[URL_TP],
        resolution: Resolution[URL_TP],
        page:       URL_TP,
        navs:       Map[String, URL_TP],
        title:      String)

    class NavigatorBackend[URL_TP <: URL_Repr](scope: BackendScope[NavigatorProps[URL_TP], NavigatorState] ) {

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

  object NavigatorCompConstrHolder {
    //Reusable if all fields are equal except routerCtl, where we use its own reusability
    private implicit def navPropsReuse[P <: URL_Repr]: Reusability[NavigatorProps[P]] = Reusability.fn {
      case ( a, b ) if a eq b => true // First because most common case and fastest
      case ( a, b )
          if a.page == b.page && a.navs == b.navs && a.title == b.title && a.resolution == b.resolution =>
        RouterCtl.reusability[P].test( a.routerCtl, b.routerCtl )
      case _ => false
    }

    //Just make the component constructor - props to be supplied later to make a component
    private def apply[
        NavigatorChildDescriptor <: URL_Repr
      ]: ReqProps[NavigatorProps[NavigatorChildDescriptor], NavigatorState, NavigatorBackend[
      NavigatorChildDescriptor
    ], TopNode] =
      ReactComponentB[NavigatorProps[NavigatorChildDescriptor]]( "Nav" )
        .initialState( NavigatorState( false ) )
        .backend( new NavigatorBackend[NavigatorChildDescriptor]( _ ) )
        .render( s => s.backend.render( s.props, s.state ) )
        .build

    val navigatorCompConstr: ReqProps[NavigatorProps[
      URL_Repr
    ], NavigatorState, NavigatorBackend[
      URL_Repr
    ], TopNode] =
      apply[URL_Repr]

  }

  object NavigatorFacade {

    type NavigatorCompConstr =
      ReactComponentU[NavigatorProps[URL_Repr], NavigatorState, NavigatorBackend[URL_Repr], TopNode]

    def getNavigatorCompConstr(mapStr2URL: Map[String, URL_Repr] )(ctl:  RouterCtl[URL_Repr],
                                                               r  :    Resolution[URL_Repr]
      ): NavigatorCompConstr = {

      val title = "Joco App"
      val np: NavigatorProps[URL_Repr] = NavigatorProps(ctl, r, r.page, mapStr2URL, title)

      NavigatorCompConstrHolder.navigatorCompConstr( np )

    }

  }
