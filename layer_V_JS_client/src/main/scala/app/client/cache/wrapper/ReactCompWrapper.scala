package app.client.cache.wrapper

import app.client.cache.entityCache.EntityCache
import app.client.ui.types.RootPageConstructorTypes.{VanillaRootPageCompConstr, WrappedRootPageCompConstr}
import app.client.ui.types.Vanilla_RootReactComponent_PhantomTypes.Vanilla_RootReactComponent_PhantomType
import app.client.ui.types.{PropsOfVanillaComp, PropsOfWrappedComp}

/**
  * Created by joco on 03/09/2017.
  */
class ReactCompWrapper(re: CacheRoot, cm: _wrapper_EntityCache_MutableState ) {

  def createWrappedRootPageCompConstructor[RootPagePageName <: Vanilla_RootReactComponent_PhantomType, Props](
      vanillaCC: VanillaRootPageCompConstr[RootPagePageName, Props]
    ): WrappedRootPageCompConstr[RootPagePageName, Props] = {

    import japgolly.scalajs.react._

    type P = PropsOfVanillaComp[Props]
    class WBackend($ : BackendScope[P, EntityCache] ) {
      def render(t: (P), statePassedToRender: EntityCache ): ReactElement =
        vanillaCC(PropsOfWrappedComp[Props, RootPagePageName](t.p, t.ctrl, statePassedToRender))

      def willMount: CallbackTo[Unit] = {
        Callback {
          re.currently_routed_page = Some( new StateSettable {
            override def setState(c: EntityCache ): Unit = $.accessDirect.setState(c)

            // ez akkor kell, ha egy getEntity visszateresenek kovetkezteben meghivunk meg egy getEntity-t
            // pl. egymasba agyazott komponensek lerajzolasa soran - ugyanis minden setState elkuld meghiv egy render-t
            // es ennek a render-nek a meghivasa folyaman keletkezhetnek ujabb readRequest-ek
          } )
        } >> $.setState( cm.getCacheMap )
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

    def getWrappedPageComponentConstructor
      : ReactComponentC.ReqProps[PropsOfVanillaComp[Props], EntityCache, WBackend, TopNode] =
      ReactComponentB[PropsOfVanillaComp[Props]]("wrapped page component")
        .initialState( cm.getCacheMap )
        .backend[WBackend]( new WBackend( _ ) )
        .renderBackend
        .componentDidMount( scope => scope.backend.didMount )
        .componentWillMount( scope => scope.backend.willMount )
        .build

    getWrappedPageComponentConstructor
  }
}
