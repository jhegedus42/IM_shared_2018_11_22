package app.client.entityCache.entityCacheV1

import app.client.entityCache.entityCacheV1.reqHandler.ReadWriteRequestHandler
import app.client.entityCache.entityCacheV1.types.RootPageConstructorTypes.{CacheInjectedComponentConstructor, CacheInjectorCompConstructor}
import app.client.entityCache.entityCacheV1.types.Vanilla_RootReactComponent_PhantomTypes.RootReactComponent_MarkerTrait
import app.client.entityCache.entityCacheV1.types.{PropsWithInjectedCache, PropsWithoutEntityReaderWriter}
import app.client.rest.commands.generalCRUD.GetEntityAJAX
import app.client.rest.commands.generalCRUD.GetEntityAJAX.ResDyn
import app.shared.SomeError_Trait
import app.shared.data.model.Entity.Entity
import app.shared.data.ref.{Ref, RefVal}
import io.circe.{Decoder, Encoder}
import scalaz.{-\/, \/-}
import slogging.LazyLogging

import scala.concurrent.Future
import scala.reflect.ClassTag

private [entityCacheV1] case class ReadRequest[E <: Entity: ClassTag](ref: Ref[E] )


private[entityCacheV1] case class UpdateRequest[E <: Entity](rv: RefVal[E] )

/**
  * f079711f_4c99b1ca
  *
  * @param newCacheMapProvider
  */
// singleton - TODO - create a singleton factory for this
class EntityCache_StateHolder(newCacheMapProvider:RootReactCompConstr_Enhancer) {


  private[this] var immutableEntityCacheMap: ImmutableMapHolder = newCacheMapProvider.createNewState

  def getState: ImmutableMapHolder = immutableEntityCacheMap

  //  def resetCache(): Unit = immutableEntityCacheMap = newCacheMapProvider.createNewEntityReaderWriter

  private[this] def updateCache[E <: Entity](key: Ref[E],
                                             cacheVal: EntityCacheVal[E],
                                             oldVal: EntityCacheVal[E]): Unit = {
    assert(immutableEntityCacheMap.map(key).equals(oldVal))
    val newCacheMap: Map[Ref[_ <: Entity], EntityCacheVal[_ <: Entity]] =
      immutableEntityCacheMap.map.updated(key, cacheVal)
    immutableEntityCacheMap = immutableEntityCacheMap.copy(map = newCacheMap)
  }

  object EntityStateChanger {

    def setNotYetLoaded[E <: Entity](ref: Ref[E]): NotYetLoaded[E] = {
      val res = NotYetLoaded(ref)
      immutableEntityCacheMap = immutableEntityCacheMap.copy(map = immutableEntityCacheMap.map.updated(ref, res))
      res
    }

    def setLoading[E <: Entity](e: NotYetLoaded[E]): Loading[E] = {

      val res: Loading[E] = Loading(e.ref)
      updateCache(e.ref, res, e)
      res

    }

    def setUpdating[E <: Entity](oldVal: Ready[E],
                                 newVal: RefVal[E]): Updating[E] = {
      val updatingTo = Updating(newVal)
      updateCache(oldVal.refVal.r, updatingTo, oldVal)
      updatingTo
    }

    def setLoaded[E <: Entity](oldVal: Loading[E], newVal: RefVal[E]): Unit =
      updateCache(newVal.r, Loaded(newVal), oldVal)

    def setUpdated[E <: Entity](oldVal: Updating[E], newVal: RefVal[E]): Unit =
      updateCache(newVal.r, Updated(newVal), oldVal)


    def setReadFailed[E <: Entity](oldVal: Loading[E], errorMsg: String): Unit =
      updateCache(oldVal.ref, ReadFailed(oldVal.ref, err = errorMsg), oldVal)

    def setUpdateFailed[E <: Entity](oldVal: Updating[E], errorMsg: String): Unit =
      updateCache(oldVal.refVal.r,
                  UpdateFailed(oldVal.refVal, err = errorMsg),
                  oldVal)

  }

}


private[entityCacheV1] class ReadReqHandler(cache: EntityCache_StateHolder, pageRerenderer: () => Unit )
    extends LazyLogging {
  private[this] var readRequests: Set[ReadRequest[_ <: Entity]] = Set()
  private[entityCacheV1] def queRequest[E <: Entity](rr: ReadRequest[E] ): Unit = {
    readRequests = readRequests + rr
    logger.trace( "in collect read requests - readRequests:" + rr )
  }

  private[entityCacheV1] def executeReadRequests(): Unit = {
    logger.trace( "in executeReadRequests read requests:" + readRequests )
    println( "137" + readRequests )

    def processRequest[E <: Entity]: ( ReadRequest[E] ) => Future[Unit] = {
      (rr: ReadRequest[E]) =>
        {
          logger.trace( "..." )
          println( "processing read request " + rr )
          import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

          val notYetLoaded: NotYetLoaded[E] =
            cache.EntityStateChanger.setNotYetLoaded( rr.ref )
          // start Future

          val res: Future[ResDyn] = GetEntityAJAX.getEntityDyn( rr.ref )

          val loading: Loading[E] = cache.EntityStateChanger.setLoading( notYetLoaded )

          res.map( {
            r: ResDyn =>
              println( s"future completed for $rr, result is $r" )
              val res: Unit = r.erv match {
                case -\/( err ) =>
                  cache.EntityStateChanger.setReadFailed[E]( loading, err.toString )
                case \/-( refValDyn ) =>
                  refValDyn.toRefVal_NoClassTagNeeded( rr.ref.dataType ) match {
                    case -\/( a: SomeError_Trait ) =>
                      cache.EntityStateChanger.setReadFailed( oldVal = loading, errorMsg = a.string )

                    case \/-( b: RefVal[E] ) =>
                      cache.EntityStateChanger.setLoaded[E]( oldVal = loading, newVal = b )
                  }
              }
              pageRerenderer() // we rerender the page after each ajax came back
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
    pageRerenderer() // we rerender the page after each ajax req has been started
  }

}


/**
  * @param map Immutable Map - Holding the current state
  *
  *
  * @param entityReadWriteRequestHandler needs to be notified about read or write requests
  *
  *
  */
case class ImmutableMapHolder(
    map:                           Map[Ref[_ <: Entity], EntityCacheVal[_ <: Entity]] = Map(),
    entityReadWriteRequestHandler: ReadWriteRequestHandler)
    extends LazyLogging {

  // def getView ...

  def getEntity[E <: Entity: ClassTag](r: Ref[E] ): EntityCacheVal[E] = {

    if (!map.contains( r )) { // csak akkor hivjuk meg ha meg nincs benne a cache-ben ...
      val readRequest: ReadRequest[E] = ReadRequest( r )
      logger.trace( "getEntity - readRequest:" + readRequest )
      entityReadWriteRequestHandler.handleReadRequest( readRequest )
    }

    val res: EntityCacheVal[_ <: Entity] = map.getOrElse( r, NotInCache( r ) )

    res.asInstanceOf[EntityCacheVal[E]]
  }

  def updateEntity[E <: Entity: ClassTag: Decoder: Encoder](rv: RefVal[E] ): Unit = {
    // 0 0d33c0acbc0240b9967f48951ddf79ed dispatch write request
    entityReadWriteRequestHandler.handleUpdateReq( UpdateRequest( rv ) )
    println( "update entity is called" + rv )

    // -- as a response to user events (say pushing button) //    0d33c0acbc0240b9967f48951ddf79ed
  }

}


trait StateSettable {
  def setState(c: ImmutableMapHolder )
}


/**
  *
  * Takes a root react component constructor which creates a component with properties without
  * EntityReaderWriter.
  *
  * This is a singleton.
  *
  */
case class RootReactCompConstr_Enhancer() extends ReadWriteRequestHandler {

  private[entityCacheV1] def createNewState: ImmutableMapHolder =
    new ImmutableMapHolder( entityReadWriteRequestHandler = this )

  //mutable - coz its a var

  // this is a singleton, below
  private[entityCacheV1] val stateProvider: EntityCache_StateHolder = new EntityCache_StateHolder( this )

  // mutable state
  // egyszerre csak 1 updateRequest futhat (fut=Future el van kuldve)

  private[entityCacheV1] var currently_routed_page: Option[StateSettable] = None

  lazy val wrapper = this

  private[entityCacheV1] val readRequestHandler: ReadReqHandler =
    new ReadReqHandler( stateProvider, reRenderCurrentlyRoutedPageComp )

  //  private
  //  def clearCache = {
  //    cache.resetCache()
  //    reRenderCurrentlyRoutedPageComp()
  //  }

  private[entityCacheV1] def reRenderCurrentlyRoutedPageComp(): Unit = {
    val c: ImmutableMapHolder = stateProvider.getState
    println( "re render with cache: " + c )

    currently_routed_page.foreach( {
      s =>
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

    type P = PropsWithoutEntityReaderWriter[Props]

    class WBackend($ : BackendScope[P, ImmutableMapHolder] ) {

      def render(t: (P), statePassedToRender: ImmutableMapHolder ): ReactElement =
        constructorOfComponent_Taking_PropertiesAndInjectedCache(
          PropsWithInjectedCache[Props, RootPagePageName]( t.p, t.ctrl, statePassedToRender )
        )

      def willMount: CallbackTo[Unit] = {

        Callback {

          currently_routed_page = Some( new StateSettable {
            override def setState(c: ImmutableMapHolder ): Unit =
              $.accessDirect.setState( c )

            // ez akkor kell, ha egy getEntity visszateresenek kovetkezteben meghivunk meg egy getEntity-t
            // pl. egymasba agyazott komponensek lerajzolasa soran - ugyanis minden setState elkuld meghiv egy render-t
            // es ennek a render-nek a meghivasa folyaman keletkezhetnek ujabb readRequest-ek
          } )

        } >> $.setState( stateProvider.getState ) // EZERT KELL NEKI STATE <<<<<<========
        // ez itt egy closure ennek a comp-nek a backend-jehez, amit oda kell adni a RequestExecutor-nak
        // hogy updatelni tudja a state-et, amikor visszajonnek a cuccok a future-bol
        // set the state of this comp. needs to the current state of the cache ?

      }

      def didMount = Callback {
        println( "did mount" );
        readRequestHandler.executeReadRequests()
      }

    }

    def getCompConstructorForRouter
      : ReactComponentC.ReqProps[PropsWithoutEntityReaderWriter[Props], ImmutableMapHolder, WBackend, TopNode] =
      ReactComponentB[PropsWithoutEntityReaderWriter[Props]]( "wrapped page component" )
        .initialState( stateProvider.getState )
        .backend[WBackend]( new WBackend( _ ) )
        .renderBackend
        .componentDidMount( scope => scope.backend.didMount )
        .componentWillMount( scope => scope.backend.willMount )
        .build

    getCompConstructorForRouter

  }

}
