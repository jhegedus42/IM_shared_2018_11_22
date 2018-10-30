package app.client.wrapper

import app.client.wrapper.types.RootPageConstructorTypes.{VanillaRootPageCompConstr, WrappedRootPageCompConstr}
import app.client.wrapper.types.Vanilla_RootReactComponent_PhantomTypes.Vanilla_RootReactComponent_PhantomType
import app.client.wrapper.types.{PropsOfVanillaComp, PropsOfWrappedComp}
import app.client.wrapper.wrapperFactory.{ReadReqHandler, ReadWriteRequestHandler, StateSettable}

/**
  * Created by joco on 03/09/2017.
  */

case class ReactCompWrapperFactory() extends  ReadWriteRequestHandler with EntityReaderWriterFactory {

  override def createNewEntityReaderWriter: EntityReaderWriter = new EntityReaderWriter(entityReadWriteRequestHandler = this)

  //mutable - coz its a var

  val cache: EntityCache_MutableState = new EntityCache_MutableState(this) //mutable state
  // egyszerre csak 1 updateRequest futhat (fut=Future el van kuldve)

  private[wrapper] var currently_routed_page: Option[StateSettable] = None

  lazy val wrapper = new ReactCompWrapper( re = this, cm = cache )

  val readRequestHandler = new ReadReqHandler(cache, reRenderCurrentlyRoutedPageComp)

  //  private
  //  def clearCache = {
  //    cache.resetCache()
  //    reRenderCurrentlyRoutedPageComp()
  //  }

  def reRenderCurrentlyRoutedPageComp(): Unit = {
    val c: EntityReaderWriter = cache.getCacheMap
    println( "re render with cache: " + c )
    currently_routed_page.foreach( {
                                     s =>
                                       s.setState( c )
                                   } )
  }

}

class ReactCompWrapper(re: ReactCompWrapperFactory, cm: EntityCache_MutableState ) {

  def createWrappedRootPageCompConstructor[RootPagePageName <: Vanilla_RootReactComponent_PhantomType, Props](
      vanillaCC: VanillaRootPageCompConstr[RootPagePageName, Props]
    ): WrappedRootPageCompConstr[RootPagePageName, Props] = {

    import japgolly.scalajs.react._

    type P = PropsOfVanillaComp[Props]

    class WBackend($ : BackendScope[P, EntityReaderWriter] ) {

      def render(t: (P), statePassedToRender: EntityReaderWriter ): ReactElement =
        vanillaCC(PropsOfWrappedComp[Props, RootPagePageName](t.p, t.ctrl, statePassedToRender))

      def willMount: CallbackTo[Unit] = {
        Callback {
          re.currently_routed_page = Some( new StateSettable {
            override def setState(c: EntityReaderWriter ): Unit = $.accessDirect.setState(c)

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
                                re.readRequestHandler.executeReadRequests()
      }

    }

    def getWrappedPageComponentConstructor
      : ReactComponentC.ReqProps[PropsOfVanillaComp[Props], EntityReaderWriter, WBackend, TopNode] =
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
