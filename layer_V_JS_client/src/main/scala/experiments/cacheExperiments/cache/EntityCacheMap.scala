package experiments.cacheExperiments.cache

import app.client.rest.commands.generalCRUD.GetEntityAJAX.getEntity
import app.shared.data.model.Entity.Entity
import app.shared.data.ref.{Ref, RefVal}
import experiments.cacheExperiments.cache.ajax.AJAXGetEntityApi.InFlight_ReadEntity
import experiments.cacheExperiments.cache.CacheStates.{CacheState, Loading}
import experiments.cacheExperiments.cache.ajax.AJAXReqInFlightMonitor
import io.circe.Decoder

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.reflect.ClassTag

/**
  * This is a map that contains cached Entities.
  *
  * @tparam E
  */

class EntityCacheMap[E <: Entity]() {
  var map: Map[Ref[E], CacheState[E]] = Map()

  def launchReadAjax(ref: Ref[E] )(implicit decoder: Decoder[RefVal[E]],ct:ClassTag[E] ): Unit = {

    // QUESTION BACKLOG => should we launch this future immediately or only after the render method has been
    // completed ?  => not yet, only if needed

    implicit def executionContext: ExecutionContextExecutor =
      scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

    /**
      *
      * Ez mit hivatott itt csinalni ? Ki hivja ezt ?
      * Letrhozza az AJAX call-t:
      */
    val ajaxCallAsFuture: Future[RefVal[E]] = {

      val res = getEntity[E]( ref )
      res
    }

    val ajaxCall = InFlight_ReadEntity( ref, ajaxCallAsFuture )
    AJAXReqInFlightMonitor.addToInFlightReqList( ajaxCall )

//    ajaxCallAsFuture.onComplete(_ => AJAXReqInFlightMonitor(Completed__ReadEntity_AjaxCall(ajaxCall)))
    // TODO3
  }

  def readEntity(refToEntity: Ref[E] ): CacheState[E] = { // 74291aeb_02f0aea6
    if (!map.contains( refToEntity )) {
      val loading = Loading( refToEntity )

//      AJAXApi.launchReadAjax(refToEntity) //TODO3 make this compile

      loading

    } else map( refToEntity )

    // TASK_19ffbc83_02f0aea6

  }
}
