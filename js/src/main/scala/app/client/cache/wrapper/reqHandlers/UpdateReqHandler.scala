package app.client.cache.wrapper.reqHandlers

import app.client.cache.entityCache.{EntityCache, EntityCacheVal, Ready, Updating}
import app.client.cache.wrapper.UpdateRequest
import app.client.rest.commands.generalCRUD.UpdateEntityAJAX
import app.shared.SomeError_Trait
import app.shared.data.model.Entity.Entity
import app.shared.data.ref.RefVal
import app.shared.rest.routes_take3.crudCommands.UpdateEntityCommCommand.UEC_Res
import io.circe.{Decoder, Encoder}

import scala.concurrent.Future
import scala.reflect.ClassTag
import scalaz.{-\/, \/-}

/**
  * Created by joco on 08/01/2018.
  */
object  UpdateReqHandler {
  private[wrapper] def launchUpdateReq[E <: Entity: ClassTag: Decoder: Encoder](
      cache:          EntityCache,
      wr:             UpdateRequest[E],
      pageRerenderer: () => Unit
    ): Unit = {
    //only one ur can be dispatched at any given time
    //  ->  this makes things simpler

    val e: EntityCacheVal[E] = cache.getCacheMap().getEntity( wr.rv.r )
    if (e.isReady()) {
      val ready:    Ready[E]    = e.asInstanceOf[Ready[E]]
      val updating: Updating[E] = cache.setUpdating( ready, wr.rv )

      val f: Future[UEC_Res[E]] = UpdateEntityAJAX.updateEntity( wr.rv )
      // ab58169c298a4c1bb18c252f092142da commit b644e0744804cc562d4c7648aafaae93ec4727e5 Tue Dec 19 02:45:20 EET 2017

      import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
      val res: Future[Unit] =
        f.map( {
          (r: UEC_Res[E]) =>
            {
              r match {
                case -\/( a: SomeError_Trait ) =>
                  cache.setUpdateFailed( updating, a.toString )
                case \/-( newVal: RefVal[E] ) =>
                  cache.setUpdated( updating, newVal )
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
