package app.client.cache.wrapper

import app.client.cache.entityCache.{EntityCache, EntityCacheMap}
import app.client.ui.pages.{Props2Vanilla, Props2Wrapped, TopPageCompType}
import app.client.ui.pages.Types.{Vanilla_CompConstr, Wrapped_CompConstr}
import app.client.ui.pages.main.root_children.materialUI_children.Pages.Page
import japgolly.scalajs.react.extra.router.RouterCtl

/**
  * Created by joco on 03/09/2017.
  */
class ReactCompWrapper(re: CacheRoot, cm: EntityCache ) {

  def wrapRootPage[PageName <: TopPageCompType, Props](
      vanillaCC: Vanilla_CompConstr[PageName, Props]
    ): Wrapped_CompConstr[PageName, Props] = {

    import japgolly.scalajs.react._

    type P = Props2Wrapped[Props]
    class WBackend($ : BackendScope[P, EntityCacheMap] ) {
      def render(t: (P), statePassedToRender: EntityCacheMap ): ReactElement =
        vanillaCC( Props2Vanilla[Props, PageName]( t.p, t.ctrl, statePassedToRender ) )

      def willMount = {
        Callback {
          re.currently_routed_page = Some( new StateSettable {
            override def setState(c: EntityCacheMap ) = $.accessDirect.setState(c)

            // ez akkor kell, ha egy getEntity visszateresenek kovetkezteben meghivunk meg egy getEntity-t
            // pl. egymasba agyazott komponensek lerajzolasa soran - ugyanis minden setState elkuld meghiv egy render-t
            // es ennek a render-nek a meghivasa folyaman keletkezhetnek ujabb readRequest-ek
          } )
        } >> $.setState( cm.getCacheMap() )
        // ez itt egy closure ennek a comp-nek a backend-jehez, amit oda kell adni a RequestExecutor-nak
        // hogy updatelni tudja a state-et, amikor visszajonnek a cuccok a future-bol
        // set the state of this comp. needs to the current state of the cache ? - do we really need this
        // if the initial state in - https://gitter.im/suzaku-io/diode?at=5963d7881c8697534a0e3fa0
        // but it does not hurt ... to have that there...
      }

      def didMount = Callback {
        println( "did mount" );
        re.readHandler.executeReadRequests()
      }

    }

    def wrappedPageComponentConstructor(): Wrapped_CompConstr[PageName, Props] =
      ReactComponentB[Props2Wrapped[Props]]( "wrapped page component" )
        .initialState( cm.getCacheMap() )
        .backend[WBackend]( new WBackend( _ ) )
        .renderBackend
        .componentDidMount( scope => scope.backend.didMount )
        .componentWillMount( scope => scope.backend.willMount )
        .build

    wrappedPageComponentConstructor
  }
}