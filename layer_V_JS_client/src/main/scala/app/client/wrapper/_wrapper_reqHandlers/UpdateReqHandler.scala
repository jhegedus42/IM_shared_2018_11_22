package app.client.wrapper._wrapper_reqHandlers

import app.client.rest.commands.generalCRUD.UpdateEntityAJAX
import app.client.wrapper.wrapperFactory._wrapper_EntityCache_MutableState
import app.client.wrapper.wrapperFactory.wrapperFactoryClass.UpdateRequest
import app.client.wrapper.{EntityCacheVal, Ready, Updating}
import app.shared.SomeError_Trait
import app.shared.data.model.Entity.Entity
import app.shared.data.ref.RefVal
import app.shared.rest.routes.crudRequests.UpdateEntityRequest.UpdateEntityRequestResult
import io.circe.{Decoder, Encoder}
import scalaz.{-\/, \/-}

import scala.concurrent.Future
import scala.reflect.ClassTag

/**
  * Created by joco on 08/01/2018.
  */
private[wrapper] object  UpdateReqHandler {
  private[wrapper] def launchUpdateReq[E <: Entity: ClassTag: Decoder: Encoder](
                                                                                 cache:          _wrapper_EntityCache_MutableState,
                                                                                 wr:             UpdateRequest[E],
                                                                                 pageRerenderer: () => Unit
    ): Unit = {
    //only one ur can be dispatched at any given time
    //  ->  this makes things simpler

    val e: EntityCacheVal[E] = cache.getCacheMap.getEntity( wr.rv.r )
    if (e.isReady()) {
      val ready:    Ready[E]    = e.asInstanceOf[Ready[E]]
      val updating: Updating[E] = cache.EntityStateChanger.setUpdating( ready, wr.rv )

      val f: Future[UpdateEntityRequestResult[E]] = UpdateEntityAJAX.updateEntity(wr.rv)
      // ab58169c298a4c1bb18c252f092142da commit b644e0744804cc562d4c7648aafaae93ec4727e5 Tue Dec 19 02:45:20 EET 2017

      import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
      val res: Future[Unit] =
        f.map( {
          (r: UpdateEntityRequestResult[E]) =>
            {
              r match {
                case -\/( a: SomeError_Trait ) =>
                  cache.EntityStateChanger.setUpdateFailed( updating, a.toString )
                case \/-( newVal: RefVal[E] ) =>
                  cache.EntityStateChanger.setUpdated( updating, newVal )
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

}
