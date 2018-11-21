package app.client.entityCache.entityCacheV1

import app.client.entityCache.entityCacheV1.state.CacheStates.{EntityCacheVal, Loaded, Loading, NotInCache, NotYetLoaded, ReadFailed, Ready, UpdateFailed, Updated, Updating}
import app.client.entityCache.entityCacheV1.types.RootPageConstructorTypes.{Depth1CompConstrWrapper, Depth2CompConstr_Alias}
import app.client.entityCache.entityCacheV1.types.componentProperties.{D1Comp_Props, Depth1CompProps_With_RouterCtl, Depth2CompProps_ELI_D1CompProps_With_RouterCtl_With_EntityCache}
import app.client.entityCache.entityCacheV1.types.entityReadWrite.{EntityReadRequestHandlerTr, EntityWriteRequestHandlerTr}
import app.client.rest.commands.generalCRUD.GetEntityAJAX.ResDyn
import app.client.rest.commands.generalCRUD.{GetEntityAJAX, UpdateEntityAJAX}
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.URL_STr
import app.shared.SomeError_Trait
import app.shared.data.model.Entity.Entity
import app.shared.data.ref.{Ref, RefVal}
import app.shared.rest.routes.crudRequests.UpdateEntityRequest.UpdateEntityRequestResult
import io.circe.{Decoder, Encoder}
import scalaz.{-\/, \/-}
import slogging.LazyLogging

import scala.concurrent.Future
import scala.reflect.ClassTag

/**
  * @param map Immutable Map - Holding the current state
  *
  * fa9def4f_b9f40805
  * @param eRWReqHler needs to be notified about read or write requests
  *                                      this is a callback function.
  */
case class CacheState(
    map:        Map[Ref[_ <: Entity], EntityCacheVal[_ <: Entity]] = Map(),
    eRWReqHler: EntityReadRequestHandlerTr with EntityWriteRequestHandlerTr)
    extends LazyLogging {

  def getEntity[E <: Entity: ClassTag](r: Ref[E] ): EntityCacheVal[E] = {
    if (!map.contains( r )) { // csak akkor hivjuk meg ha meg nincs benne a cache-ben ...
      eRWReqHler.handleReadRequest( r )
    }

    val res: EntityCacheVal[_ <: Entity] = map.getOrElse( r, NotInCache( r ) )
    res.asInstanceOf[EntityCacheVal[E]]
  }

  def updateEntity[E <: Entity: ClassTag: Decoder: Encoder](rv: RefVal[E] ): Unit = {
    eRWReqHler.handleUpdateReq( rv )
  }

}

/**
  *
  * Takes a root react component constructor which creates a component with properties without
  * EntityReaderWriter.
  *
  * This is a singleton. //TODO make this az a singleton trait make this az a singleton trait
  *
  */
object RootReactCompConstr_Enhancer {
  lazy val wrapper = new RootReactCompConstr_Enhancer()
}

trait ReRenderInitiator {
  // TODO interface that provides support for initiating a re-render of the root page
  def initiateReRender(): Unit

}

class RootReactCompConstr_Enhancer() extends LazyLogging {
  // c26d7190_83dd163e

  // TODO ettol megszabadulni
  // illetve a fenti trait-ekkel implementalni

  private trait StateChangerInterfaceForCurrentlyRoutedPage[_ <: URL_STr] {

    // mi a faszomat csinal ez ????
    // ki hasznalja ezt ?
    // miert hasznalja ezt ?

    def setState(c: CacheState )
  }

  def getSnapShotOfCurrentState: CacheState = cacheState

  private var cacheState: CacheState =
    new CacheState( eRWReqHler = ??? )

  private var currently_routed_page // af18ce29_b9f40805
    : Option[
      StateChangerInterfaceForCurrentlyRoutedPage[_ <: URL_STr]
    ] = None

  private var readRequests: Set[RequestHandling.ReadRequest[_ <: Entity]] = Set()

  object RequestHandling {

    def handleReadRequest[E <: Entity: ClassTag](r: Ref[E] ): Unit = {
      readRequests = readRequests + ReadRequest( r )
    }

    def handleUpdateReq[E <: Entity: ClassTag: Decoder: Encoder](
        rv: RefVal[E]
      ): Unit = // mutates cache, rerenders page
      {

        def launchUpdateReq[E <: Entity: ClassTag: Decoder: Encoder](
            wr:             UpdateRequest[E],
            pageRerenderer: () => Unit
          ): Unit = {
          //only one ur can be dispatched at any given time
          //  ->  this makes things simpler

          val e: EntityCacheVal[E] = getSnapShotOfCurrentState.getEntity( wr.rv.r )
          if (e.isReady) {
            val ready:    Ready[E]    = e.asInstanceOf[Ready[E]]
            val updating: Updating[E] = EntityStateChanger.setUpdating( ready, wr.rv )

            val f: Future[UpdateEntityRequestResult[E]] = UpdateEntityAJAX.updateEntity( wr.rv )
            // ab58169c298a4c1bb18c252f092142da commit b644e0744804cc562d4c7648aafaae93ec4727e5 Tue Dec 19 02:45:20 EET 2017

            import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
            val res: Future[Unit] =
              f.map( {
                (r: UpdateEntityRequestResult[E]) =>
                  {
                    r match {
                      case -\/( a: SomeError_Trait ) =>
                        EntityStateChanger.setUpdateFailed( updating, a.toString )
                      case \/-( newVal: RefVal[E] ) =>
                        EntityStateChanger.setUpdated( updating, newVal )
                    }
                    pageRerenderer()
                  }
              } )
          } else {
            println(
              "update request was not executed coz the to be updatedable cache cell was not ready (updated or loaded)"
            )
          }
          pageRerenderer()
        }

        launchUpdateReq( UpdateRequest( rv ), reRenderCurrentlyRoutedPageCB )
        println( "update entity is called" + rv )
      }

    case class ReadRequest[E <: Entity: ClassTag](ref: Ref[E] )

    private case class UpdateRequest[E <: Entity](rv: RefVal[E] )

    def executeReadRequests(): Unit = {
      logger.trace( "in executeReadRequests read requests:" + readRequests )
      println( "137" + readRequests )

      def processRequest[E <: Entity]: ( ReadRequest[E] ) => Future[Unit] = {
        (rr: ReadRequest[E]) =>
          {
            logger.trace( "..." )
            println( "processing read request " + rr )
            import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

            val notYetLoaded: NotYetLoaded[E] =
              EntityStateChanger.setNotYetLoaded( rr.ref )
            // start Future

            val res: Future[ResDyn] = GetEntityAJAX.getEntityDyn( rr.ref )

            val loading: Loading[E] = EntityStateChanger.setLoading( notYetLoaded )

            res.map( {
              r: ResDyn =>
                println( s"future completed for $rr, result is $r" )
                val res: Unit = r.erv match {
                  case -\/( err ) =>
                    EntityStateChanger.setReadFailed[E]( loading, err.toString )
                  case \/-( refValDyn ) =>
                    refValDyn.toRefVal_NoClassTagNeeded( rr.ref.dataType ) match {
                      case -\/( a: SomeError_Trait ) =>
                        EntityStateChanger.setReadFailed( oldVal = loading, errorMsg = a.string )

                      case \/-( b: RefVal[E] ) =>
                        EntityStateChanger.setLoaded[E]( oldVal = loading, newVal = b )
                    }
                }
                reRenderCurrentlyRoutedPageCB() // we rerender the page after each ajax came back
            } )
            //637e3709d2fa4bd19e9167d45b58c425 commit b644e0744804cc562d4c7648aafaae93ec4727e5 Mon Dec 18 00:18:55 EET 2017
          }
      }

      readRequests.foreach(
        (x: ReadRequest[_ <: Entity]) => {

          processRequest( x ) //we launch an AJAX call for each entity to be read
        }
      )

      readRequests = Set()
      reRenderCurrentlyRoutedPageCB() // we rerender the page after each ajax req has been started

    }

  }

  private def updateCache[E <: Entity](
      key:      Ref[E],
      cacheVal: EntityCacheVal[E],
      oldVal:   EntityCacheVal[E]
    ): Unit = {

    assert( cacheState.map( key ).equals( oldVal ) )
    val newCacheMap: Map[Ref[_ <: Entity], EntityCacheVal[_ <: Entity]] =
      cacheState.map.updated( key, cacheVal )
    cacheState = cacheState.copy( map = newCacheMap )
  }

  private object EntityStateChanger {

    def setNotYetLoaded[E <: Entity](ref: Ref[E] ): NotYetLoaded[E] = {
      val res = NotYetLoaded( ref )

      cacheState = cacheState.copy( map = cacheState.map.updated( ref, res ) )

      res
    }

    def setLoading[E <: Entity](e: NotYetLoaded[E] ): Loading[E] = {

      val res: Loading[E] = Loading( e.ref )
      updateCache( e.ref, res, e )
      res

    }

    def setUpdating[E <: Entity](oldVal: Ready[E], newVal: RefVal[E] ): Updating[E] = {
      val updatingTo = Updating( newVal )
      updateCache( oldVal.refVal.r, updatingTo, oldVal )
      updatingTo
    }

    def setLoaded[E <: Entity](oldVal: Loading[E], newVal: RefVal[E] ): Unit =
      updateCache( newVal.r, Loaded( newVal ), oldVal )

    def setUpdated[E <: Entity](oldVal: Updating[E], newVal: RefVal[E] ): Unit =
      updateCache( newVal.r, Updated( newVal ), oldVal )

    def setReadFailed[E <: Entity](oldVal: Loading[E], errorMsg: String ): Unit =
      updateCache( oldVal.ref, ReadFailed( oldVal.ref, err = errorMsg ), oldVal )

    def setUpdateFailed[E <: Entity](oldVal: Updating[E], errorMsg: String ): Unit =
      updateCache( oldVal.refVal.r, UpdateFailed( oldVal.refVal, err = errorMsg ), oldVal )

  }

  private val reRenderCurrentlyRoutedPageCB: () => Unit = () => {

    val c: CacheState = getSnapShotOfCurrentState //TODO mi ez az erhetetlen fassage ???

    println( "re render with cache: " + c )

    currently_routed_page.foreach( {
      s: StateChangerInterfaceForCurrentlyRoutedPage[
        _ <: URL_STr
      ] =>
        s.setState( c )
    } )

  }

  /**
    *
    *
    * @param depth2CompConstr This is the constructor into which the cache has been injected.
    *                                More precisely, the cache was injected into it's parameters.
    * @tparam URL_TP                      This is the name of the root page which is defined by
    *                                              the component created by [[create_Depth1CompConstr_by_wrapping_Depth2CompConstructor()]].
    * @tparam D1CompProps_TP These are the properties which will be extended with `PropsWithEntityReaderWriter`
    *                                              and will be also provided to the component created by the "enhanced constructor".
    * @return a Root React Comp Constructor that creates a Component which will be
    *
    *         mounted by the router as the Root Component.
    *
    *         This is the injector component that has the "real" component as a child, into which
    *         the cache is injected.
    *
    *         This component injects the cache into the child component.
    *
    *         This component has a state EntityReaderWriter_State_To_React_Comp.
    *
    *
    */
  def create_Depth1CompConstr_by_wrapping_Depth2CompConstructor[
      URL_TP <: URL_STr,
      D1CompProps_TP <: D1Comp_Props
    ](depth2CompConstr: Depth2CompConstr_Alias[
        URL_TP,
        D1CompProps_TP
      ]
    ): Depth1CompConstrWrapper[URL_TP, D1CompProps_TP] = {
    import japgolly.scalajs.react._

    type PropsDepth1Comp =
      Depth1CompProps_With_RouterCtl[D1CompProps_TP]

    class WBackend(backendScope: BackendScope[PropsDepth1Comp, CacheState] ) {

      /**
        * @param t
        * @param statePassedToRender Who passes this state to this method ?
        * @return
        */
      def render(t: PropsDepth1Comp, statePassedToRender: CacheState ): ReactElement =
        depth2CompConstr(
          Depth2CompProps_ELI_D1CompProps_With_RouterCtl_With_EntityCache[
            D1CompProps_TP,
            URL_TP
          ](
            t.p,
            t.ctrl,
            statePassedToRender
          )
        )

      def willMount(): CallbackTo[Unit] = {

        Callback {

          // itt mi adunk egy
          currently_routed_page = Some( new StateChangerInterfaceForCurrentlyRoutedPage[URL_TP] {
            override def setState(c: CacheState ): Unit =
              backendScope.accessDirect.setState( c )

            // fcaab126_b9f40805

            // ez akkor kell, ha egy getEntity visszateresenek kovetkezteben meghivunk
            //
            //
            // ha egy get entity visszajon , akkor mi tortenik ?
            //
            //
            // meg egy getEntity-t
            // pl. egymasba agyazott komponensek lerajzolasa soran - ugyanis minden setState elkuld meghiv egy render-t
            // es ennek a render-nek a meghivasa folyaman keletkezhetnek ujabb readRequest-ek

          } )

        } >> backendScope.setState( getSnapShotOfCurrentState ) // EZERT KELL NEKI STATE <<<<<<========
        // ez itt egy closure ennek a comp-nek a backend-jehez,
        // hogy updatelni tudja a state-et, amikor visszajonnek a cuccok a future-bol
        //
        // de mi a fasznak kell ez ?
        // miert kell a komponens state-jet updatelni ?
        //
        //
        // a komponensnek nem kell state
        // a state az itt van
        // minden state itt van
        // amiben az entitik tarolodnak
        //
        // ez az egesz baszas nem is kell
        //
        // csak annyi kell, hogy a render-t ujra meg kell hivni, ha visszajott egy future
        //
        // de hol hivjuk meg azt a render-t ?
        // elvileg itt, azaltal, hogy set state-et hivunk
        //
        // tehat lenyegeben a Root Comp - ben van a state
        // vagy legalabbis ott van egy state a Cache - bol
        //
        // de erre nincsen szukseg TODO !!!!
        // ====== >>>>> KISZEDNI A STATE-ET a ROOT Comp-bol
        //

      }

      def didMount = Callback {
        println( "did mount" );
        RequestHandling.executeReadRequests()
      }

    }

    def getCompConstructorForRouter: ReactComponentC.ReqProps[Depth1CompProps_With_RouterCtl[ D1CompProps_TP ],
                                          CacheState, WBackend, TopNode] =
      ReactComponentB[Depth1CompProps_With_RouterCtl[ D1CompProps_TP ]](
        "wrapped page component"
      ).initialState( getSnapShotOfCurrentState )
        .backend[WBackend]( new WBackend( _ ) )
        .renderBackend
        .componentDidMount( scope => scope.backend.didMount )
        .componentWillMount( scope => scope.backend.willMount )
        .build

    val  res :Depth1CompConstrWrapper[URL_TP, D1CompProps_TP]  =
       Depth1CompConstrWrapper[URL_TP, D1CompProps_TP](getCompConstructorForRouter)
    res
  }

}
