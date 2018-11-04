package app.client.entityCache.entityCacheV1

import app.client.rest.commands.generalCRUD.GetEntityAJAX
import app.client.rest.commands.generalCRUD.GetEntityAJAX.ResDyn
import app.client.entityCache.entityCacheV1.reqHandler.{ReadWriteRequestHandler, UpdateRequest}
import app.client.entityCache.entityCacheV1.types.RootPageConstructorTypes.{
  Constructor_Providing_ExtendedProperties,
  Constructor_Used_By_The_Parent_Component
}
import app.client.entityCache.entityCacheV1.types.Vanilla_RootReactComponent_PhantomTypes.RootReactComponent_MarkerTrait
import app.client.entityCache.entityCacheV1.types.{
  PropsWithoutEntityReaderWriter,
  PropsWithInjectedEntityReaderWriter
}
import app.shared.SomeError_Trait
import app.shared.data.model.Entity.Entity
import app.shared.data.ref.{Ref, RefVal}
import io.circe.{Decoder, Encoder}
import scalaz.{-\/, \/-}
import slogging.LazyLogging
import scala.concurrent.Future
import scala.reflect.ClassTag

private[entityCacheV1] class ReadReqHandler(cache: EntityCache_MutableState, pageRerenderer: () => Unit )
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

case class EntityReaderWriter_State_To_React_Comp(
    map:                           Map[Ref[_ <: Entity], EntityCacheVal[_ <: Entity]] = Map(),
    entityReadWriteRequestHandler: ReadWriteRequestHandler)
    extends LazyLogging {

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
  def setState(c: EntityReaderWriter_State_To_React_Comp )
}

/**
  *
  * Takes a root react component constructor which creates a component with properties without
  * EntityReaderWriter.
  *
  */
case class RootReactCompConstr_Enhancer() extends ReadWriteRequestHandler {

  private[entityCacheV1] def createNewEntityReaderWriter: EntityReaderWriter_State_To_React_Comp =
    new EntityReaderWriter_State_To_React_Comp( entityReadWriteRequestHandler = this )

  //mutable - coz its a var

  private[entityCacheV1] val cache: EntityCache_MutableState = new EntityCache_MutableState( this )

  // mutable state
  // egyszerre csak 1 updateRequest futhat (fut=Future el van kuldve)

  private[entityCacheV1] var currently_routed_page: Option[StateSettable] = None

  lazy val wrapper = this

  private[entityCacheV1] val readRequestHandler: ReadReqHandler =
    new ReadReqHandler( cache, reRenderCurrentlyRoutedPageComp )

  //  private
  //  def clearCache = {
  //    cache.resetCache()
  //    reRenderCurrentlyRoutedPageComp()
  //  }

  private[entityCacheV1] def reRenderCurrentlyRoutedPageComp(): Unit = {
    val c: EntityReaderWriter_State_To_React_Comp = cache.getCacheMap
    println( "re render with cache: " + c )

    currently_routed_page.foreach( {
      s =>
        s.setState( c )
    } )

  }

  /**
    * @param vanillaCC
    *
    * @tparam RootPagePageName This is the name of the root page which is defined by the component created by this
    *                          constructor.
    *
    * @tparam Props These are the properties which will be extended with `PropsWithEntityReaderWriter`
    *               and will be also provided to the component created by the "enhanced constructor".
    *
    * @return a Root React Comp Constructor that provides `PropsWithEntityReaderWriter` to the component
    *         which it constructs.
    */
  def create_Enhanced_RootReactComp_Constructor[RootPagePageName <: RootReactComponent_MarkerTrait, Props](
      vanillaCC: Constructor_Providing_ExtendedProperties[RootPagePageName, Props]
    ): Constructor_Used_By_The_Parent_Component[RootPagePageName, Props] = {

    import japgolly.scalajs.react._

    type P = PropsWithoutEntityReaderWriter[Props]

    class WBackend($ : BackendScope[P, EntityReaderWriter_State_To_React_Comp] ) {

      def render(t: (P), statePassedToRender: EntityReaderWriter_State_To_React_Comp ): ReactElement =
        vanillaCC(
          PropsWithInjectedEntityReaderWriter[Props, RootPagePageName]( t.p, t.ctrl, statePassedToRender )
        )

      def willMount: CallbackTo[Unit] = {
        Callback {
          currently_routed_page = Some( new StateSettable {
            override def setState(c: EntityReaderWriter_State_To_React_Comp ): Unit =
              $.accessDirect.setState( c )

            // ez akkor kell, ha egy getEntity visszateresenek kovetkezteben meghivunk meg egy getEntity-t
            // pl. egymasba agyazott komponensek lerajzolasa soran - ugyanis minden setState elkuld meghiv egy render-t
            // es ennek a render-nek a meghivasa folyaman keletkezhetnek ujabb readRequest-ek
          } )
        } >> $.setState( cache.getCacheMap )
        // ez itt egy closure ennek a comp-nek a backend-jehez, amit oda kell adni a RequestExecutor-nak
        // hogy updatelni tudja a state-et, amikor visszajonnek a cuccok a future-bol
        // set the state of this comp. needs to the current state of the cache ? - do we really need this
        // if the initial state in - https://gitter.im/suzaku-io/diode?at=5963d7881c8697534a0e3fa0
        // but it does not hurt ... to have that there...
      }

      def didMount = Callback {
        println( "did mount" );
        readRequestHandler.executeReadRequests()
      }

    }

    def getWrappedPageComponentConstructor
      : ReactComponentC.ReqProps[PropsWithoutEntityReaderWriter[Props], EntityReaderWriter_State_To_React_Comp, WBackend, TopNode] =
      ReactComponentB[PropsWithoutEntityReaderWriter[Props]]( "wrapped page component" )
        .initialState( cache.getCacheMap )
        .backend[WBackend]( new WBackend( _ ) )
        .renderBackend
        .componentDidMount( scope => scope.backend.didMount )
        .componentWillMount( scope => scope.backend.willMount )
        .build

    getWrappedPageComponentConstructor

  }

}
