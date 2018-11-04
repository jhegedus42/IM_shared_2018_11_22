package app.client.entityCache.entityCacheV1

import app.client.entityCache.entityCacheV1.types.CacheStates.{EntityCacheVal, Loaded, Loading, NotInCache, NotYetLoaded, ReadFailed, Ready, UpdateFailed, Updated, Updating}
import app.client.entityCache.entityCacheV1.types.EntityReaderWriter
import app.client.entityCache.entityCacheV1.types.RootPageConstructorTypes.{CacheInjectedComponentConstructor, CacheInjectorCompConstructor}
import app.client.entityCache.entityCacheV1.types.Vanilla_RootReactComponent_PhantomTypes.RootReactComponent_MarkerTrait
import app.client.entityCache.entityCacheV1.types.componentProperties.{PropsGivenByTheRouter_To_Depth1Component, PropsWithInjectedCache_Fed_To_Depth2Comp}
import app.client.rest.commands.generalCRUD.GetEntityAJAX.ResDyn
import app.client.rest.commands.generalCRUD.{GetEntityAJAX, UpdateEntityAJAX}
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
  *
  * @param entityReadWriteRequestHandler needs to be notified about read or write requests
  *                                      a callback function.
  */


case class CacheState(
    map:                           Map[Ref[_ <: Entity], EntityCacheVal[_ <: Entity]] = Map(),
    entityReadWriteRequestHandler: RootReactCompConstr_Enhancer)
    extends LazyLogging {

  // def getView ...

  def getEntity[E <: Entity: ClassTag](r: Ref[E] ): EntityCacheVal[E] = {
    if (!map.contains( r )) { // csak akkor hivjuk meg ha meg nincs benne a cache-ben ...
      entityReadWriteRequestHandler.RequestHandling.handleReadRequest( r )
    }

    val res: EntityCacheVal[_ <: Entity] = map.getOrElse( r, NotInCache( r ) )
    res.asInstanceOf[EntityCacheVal[E]]
  }

  def updateEntity[E <: Entity: ClassTag: Decoder: Encoder](rv: RefVal[E] ): Unit = {
    entityReadWriteRequestHandler.RequestHandling.handleUpdateReq( rv )
  }

}

/**
  *
  * Takes a root react component constructor which creates a component with properties without
  * EntityReaderWriter.
  *
  * This is a singleton.
  *
  */

object RootReactCompConstr_Enhancer {
  lazy val wrapper = new RootReactCompConstr_Enhancer()
}

class RootReactCompConstr_Enhancer() extends LazyLogging with EntityReaderWriter {

  trait StateSettable {
    def setState(c: CacheState )
  }

  def getState: CacheState = cacheState

  private var cacheState: CacheState =
    new CacheState( entityReadWriteRequestHandler = this )
  private var currently_routed_page: Option[StateSettable]         = None
  private var readRequests:          Set[RequestHandling.ReadRequest[_ <: Entity]] = Set()

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

        val e: EntityCacheVal[E] = getState.getEntity( wr.rv.r )
        if (e.isReady()) {
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

      launchUpdateReq( UpdateRequest( rv ), reRenderCurrentlyRoutedPage )
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
                reRenderCurrentlyRoutedPage() // we rerender the page after each ajax came back
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
      reRenderCurrentlyRoutedPage() // we rerender the page after each ajax req has been started

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
  private val reRenderCurrentlyRoutedPage: () => Unit = () => {

    val c: CacheState = getState

    println( "re render with cache: " + c )

    currently_routed_page.foreach( {
      s: StateSettable =>
        s.setState( c )
    } )

  }

  /**
    *
    *
    * @param constructorOfComponent_Taking_PropertiesAndInjectedCache This is the constructor into which the cache has been injected.
    *                                More precisely, the cache was injected into it's parameters.
    *
    *
    * @tparam RootPagePageName This is the name of the root page which is defined by the component created by this
    *                          constructor.
    *
    * @tparam Props These are the properties which will be extended with `PropsWithEntityReaderWriter`
    *               and will be also provided to the component created by the "enhanced constructor".
    *
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
  def createCacheInjectorCompConstructor[RootPagePageName <: RootReactComponent_MarkerTrait, Props](
      constructorOfComponent_Taking_PropertiesAndInjectedCache: CacheInjectedComponentConstructor[
        RootPagePageName,
        Props
      ]
    ): CacheInjectorCompConstructor[RootPagePageName, Props] = {

    import japgolly.scalajs.react._

    type P = PropsGivenByTheRouter_To_Depth1Component[Props]

    class WBackend($ : BackendScope[P, CacheState] ) {

      def render(t: (P), statePassedToRender: CacheState ): ReactElement =
        constructorOfComponent_Taking_PropertiesAndInjectedCache(
          PropsWithInjectedCache_Fed_To_Depth2Comp[Props, RootPagePageName](
            t.p,
            t.ctrl,
            statePassedToRender
          )
        )

      def willMount: CallbackTo[Unit] = {

        Callback {

          currently_routed_page = Some( new StateSettable {
            override def setState(c: CacheState ): Unit =
              $.accessDirect.setState( c )

            // ez akkor kell, ha egy getEntity visszateresenek kovetkezteben meghivunk meg egy getEntity-t
            // pl. egymasba agyazott komponensek lerajzolasa soran - ugyanis minden setState elkuld meghiv egy render-t
            // es ennek a render-nek a meghivasa folyaman keletkezhetnek ujabb readRequest-ek
          } )

        } >> $.setState( getState ) // EZERT KELL NEKI STATE <<<<<<========
        // ez itt egy closure ennek a comp-nek a backend-jehez, amit oda kell adni a RequestExecutor-nak
        // hogy updatelni tudja a state-et, amikor visszajonnek a cuccok a future-bol
        // set the state of this comp. needs to the current state of the cache ?

      }

      def didMount = Callback {
        println( "did mount" );
        RequestHandling.executeReadRequests()
      }

    }

    def getCompConstructorForRouter
      : ReactComponentC.ReqProps[PropsGivenByTheRouter_To_Depth1Component[Props], CacheState, WBackend, TopNode] =
      ReactComponentB[PropsGivenByTheRouter_To_Depth1Component[Props]]( "wrapped page component" )
        .initialState( getState )
        .backend[WBackend]( new WBackend( _ ) )
        .renderBackend
        .componentDidMount( scope => scope.backend.didMount )
        .componentWillMount( scope => scope.backend.willMount )
        .build

    getCompConstructorForRouter

  }

  override def getEntity[E <: Entity : ClassTag](r: Ref[E]): EntityCacheVal[E] = ???

  override def updateEntity[E <: Entity : ClassTag : Decoder : Encoder](rv: RefVal[E]): Unit = ???
}
