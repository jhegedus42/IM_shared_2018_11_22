package experiments.cacheExperiments.cache

import app.shared.SomeError_Trait
import app.shared.data.model.Entity.Entity
import app.shared.data.model.LineText
import app.shared.data.ref.{Ref, RefVal}
import app.testHelpersShared.data.TestEntities
import experiments.cacheExperiments.cache.CacheStates.{CacheState, Loading}
import scalaz.\/

import scala.concurrent.{ExecutionContextExecutor, Future}

//object Cache {
//  var requests: List[Ref[LineText]] = List()
//
//  def askForLineText(r: Ref[LineText] ): Unit = {
//    requests = r :: requests
//  }
//}

case class ReRenderTriggerer(triggerReRender: Unit => Unit )

object AJAXApi {

  case class InFlightEntityReadAjaxRequest[E <: Entity](ref: Ref[E] )

  def getAjaxRequestFuture[E <: Entity](
      ref: Ref[E]
    ): ( Future[Option[RefVal[E]]], InFlightEntityReadAjaxRequest[E] ) = {
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
    ajaxRequestFuture
  }

}

object CacheStates {
  sealed trait CacheState[E]
  case class Loading[E](r: Ref[E] ) extends CacheState[E]
  case class Loaded[E](r:  Ref[E], refVal: RefVal[E] ) extends CacheState[E]
}

class EntityCacheMap[E <: Entity]() {
  var map: Map[Ref[E], CacheState[E]] = Map()

  def handleAjaxReqSent(res:     Ref[E] )            = ???
  def handleAjaxReqReturned(res: Option[RefVal[E]] ) = ???
  // IF all our pending requests have arrived then we remove ourselves from the
  // global cache's register, saying that we are happy and ready, and if all other
  // EntityCacheMap-s are also happy and ready, then the global cache can trigger
  // a react re-render

  def readEntity(refToEntity: Ref[E] ): CacheState[E] = { // 74291aeb_02f0aea6
    if (!map.contains( refToEntity )) {
      val loading = Loading( refToEntity )

      AJAXApi.getAjaxRequestFuture( refToEntity ).map( ( r, req_descr ) => handleAjaxReqReturned( r ) )

      // TODO ... continue here ^^^^

      // TODO send "message" to the Cache that we are in the state of having at least one
      // outstanding/pending/in-flight AJAX request

      loading
    } else map.get( refToEntity )

    // TASK_19ffbc83_02f0aea6

  }
}

object Cache {

  // cache will have a separate map for each entity
  // for each view
  // ezek adnak egy type safety-t, nem kell kasztolgatni, az is latszik tisztan, hogy milyen
  // entity-t vannak hasznalatban

  def ajaxHasCompleted() = ???
  var reRenderTriggerer: Option[ReRenderTriggerer] = None

}
