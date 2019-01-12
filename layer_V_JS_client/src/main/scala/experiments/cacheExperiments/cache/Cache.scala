package experiments.cacheExperiments.cache

import app.shared.SomeError_Trait
import app.shared.data.model.Entity.Entity
import app.shared.data.model.LineText
import app.shared.data.ref.uuid.UUID
import app.shared.data.ref.{Ref, RefVal}
import app.testHelpersShared.data.TestEntities
import experiments.cacheExperiments.cache.AJAXApi.InFlightEntityReadAjaxRequest
import experiments.cacheExperiments.cache.CacheStates.{CacheState, Loading}
import scalaz.\/

import scala.concurrent.{ExecutionContextExecutor, Future}

case class ReRenderTriggerer(triggerReRender: Unit => Unit )

/**
  * This is watching over the ajax requests and what they are doing.
  * If the number of ajax requests drops to zero then we trigger a re-render
  * // TODO ^^^^
  */

object AJAXRequestsWatcher {
  var reRenderTriggerer: Option[ReRenderTriggerer] = None
  // TODO somebody ^^^ should give this a good init value ^^^
  // this changes ^^^^ because the top component changes (unless the
  // top component that I will re-render is the router... but I might just
  // simply skip the router and all that crap.

  var inFlightEntityReadAjaxRequests: Set[InFlightEntityReadAjaxRequest[_ <: Entity]] = Set()

  def handleAjaxReqHasBeenLaunched[E<:Entity](descriptor: InFlightEntityReadAjaxRequest[E] ): Unit =
  {
    val newVal=inFlightEntityReadAjaxRequests+descriptor
    inFlightEntityReadAjaxRequests=newVal
  }

  /**
    *
    * @param inFlightEntityReadAjaxRequest - honnan jon ez ?
    * @tparam E
    */
  def ajaxHasCompleted[E <: Entity](
      inFlightEntityReadAjaxRequest: InFlightEntityReadAjaxRequest[E]
    ): Unit = {
    val newVal = inFlightEntityReadAjaxRequests - inFlightEntityReadAjaxRequest
    inFlightEntityReadAjaxRequests = newVal
    if(inFlightEntityReadAjaxRequests.isEmpty) reRenderTriggerer.get.triggerReRender()
  }

}

object AJAXApi {

  case class InFlightEntityReadAjaxRequest[E <: Entity](ref: Ref[E], futureUUID: UUID = UUID.random() )

  type Res[E] = ( Future[Option[RefVal[E]]], InFlightEntityReadAjaxRequest[E] )

  def launchAjaxRequestFuture[E <: Entity](ref: Ref[E] ): Res[E] = {

    implicit def executionContext: ExecutionContextExecutor =
      scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
    import app.client.rest.commands.generalCRUD.GetEntityAJAX.getEntity
    import io.circe.generic.auto._

    val ajaxRequestFuture: Future[Option[RefVal[E]]] = getEntity[E]( ref ).map(
      x => {
        println( s"az entity visszavage az AJAXApi-ban $x" )
        val lt:  \/[SomeError_Trait, RefVal[E]] = x
        val res: Option[RefVal[E]]              = lt.toOption
        res
      }
    )

    ( ajaxRequestFuture, InFlightEntityReadAjaxRequest( ref ) )
  }

}

object CacheStates {
  sealed trait CacheState[E]
  case class Loading[E](r: Ref[E] ) extends CacheState[E]
  case class Loaded[E](r:  Ref[E], refVal: RefVal[E] ) extends CacheState[E]
}



/**
  * This is a map that contains cached Entities.
  * @tparam E
  */

class EntityCacheMap[E <: Entity]() {
  var map: Map[Ref[E], CacheState[E]] = Map()

  def handleAjaxReqSent(reqDesc:InFlightEntityReadAjaxRequest[E]): Unit =
    AJAXRequestsWatcher.handleAjaxReqHasBeenLaunched(reqDesc)

  def handleAjaxReqReturned(ajaxReqSentAndReturned: InFlightEntityReadAjaxRequest[E] ): Unit =
    AJAXRequestsWatcher.ajaxHasCompleted( ajaxReqSentAndReturned )

  // IF all our pending requests have arrived then we remove ourselves from the
  // global cache's register, saying that we are happy and ready, and if all other
  // EntityCacheMap-s are also happy and ready, then the global cache can trigger
  // a react re-render

  def readEntity(refToEntity: Ref[E] ): CacheState[E] = { // 74291aeb_02f0aea6
    if (!map.contains( refToEntity )) {
      val loading = Loading( refToEntity )
      val ( t1: Future[Option[RefVal[E]]], t2: AJAXApi.InFlightEntityReadAjaxRequest[E] ) =
        AJAXApi.launchAjaxRequestFuture( refToEntity )
      handleAjaxReqSent(t2)
      t1.map( _ => handleAjaxReqReturned(t2)) // we are mapping here to get a side effect
      loading
    } else map( refToEntity )

    // TASK_19ffbc83_02f0aea6

  }
}

object Cache {

  // erre lehetni irni type class-okat: vmi altalanos getEntity
  // metodust, azaz pl. attol fuggoen h. milyen entity't ker a react comp mas instance hivodik meg...
  // de ezt majd irjuk meg azutan ha a konkret dolgok kesz vannak



  // cache will have a separate map for each entity
  // for each view
  // ezek adnak egy type safety-t, nem kell kasztolgatni, az is latszik tisztan, hogy milyen
  // entity-t vannak hasznalatban


}
