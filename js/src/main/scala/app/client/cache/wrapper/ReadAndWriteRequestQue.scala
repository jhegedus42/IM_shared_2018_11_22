package app.client.cache.wrapper

//import app.client.cache.RequestProcessor.{VanillaPageComponent_ReactCompConstructor, WrappedPageComponent_ReactCompConstructor}
import app.client.cache.{Cache, CacheMap, CacheVal, Loaded, Loading, NotYetLoaded, Ready, Updating}
import app.client.rest.commands.generalCRUD.UpdateEntityAJAX
import app.shared.SomeError_Trait
import app.shared.model.Entity.Entity
import app.shared.model.ref.{Ref, RefVal}
import app.shared.rest.routes_take3.crudCommands.GetEntityCommand
import app.shared.rest.routes_take3.crudCommands.UpdateEntityCommCommand.UEC_Res
import io.circe.{Decoder, Encoder}

import scala.concurrent.Future
import scala.reflect.ClassTag
import scalaz.{-\/, \/-}
//todolater make this untyped ... RefDyn
case class ReadRequest[E <: Entity: ClassTag](ref: Ref[E])

case class UpdateRequest[E <: Entity](rv: RefVal[E])



trait StateSettable {
  def setState(c: CacheMap)
}


class ReadAndWriteRequestQue  {

  private[this]     val cache = new Cache(this) //mutable state
  // egyszerre csak 1 updateRequest futhat (fut=Future el van kuldve)

  private[wrapper]  var currently_routed_page: Option[StateSettable] = None

  lazy              val wrapper = new ReactCompWrapper(re = this, cm = cache)

  private[wrapper]  val readQue = new ReadRequestQue(cache, reRenderCurrentlyRoutedPageComp _)

  def clearCache = {
    cache.resetCache()
    reRenderCurrentlyRoutedPageComp()
  }

  private[cache] def launchUpdateReq[
      E <: Entity: ClassTag: Decoder: Encoder](wr: UpdateRequest[E]): Unit = {
    //only one ur can be dispatched at any given time
    //  ->  this makes things simpler

    val e: CacheVal[E] = cache.getCacheMap().getEntity(wr.rv.r)
    if (e.isReady() ) {
      val ready: Ready[E] = e.asInstanceOf[Ready[E]]
      val updating: Updating[E] = cache.setUpdating(ready, wr.rv)

      val f: Future[UEC_Res[E]] = UpdateEntityAJAX.updateEntity(wr.rv)
     // ab58169c298a4c1bb18c252f092142da commit b644e0744804cc562d4c7648aafaae93ec4727e5 Tue Dec 19 02:45:20 EET 2017


      import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
      val res: Future[Unit] =
        f.map({ (r: UEC_Res[E]) =>
          {
            r match {
              case -\/(a: SomeError_Trait) =>
                cache.setUpdateFailed(updating, a.toString)
              case \/-(newVal: RefVal[E]) =>
                cache.setUpdated(updating, newVal)
            }
            reRenderCurrentlyRoutedPageComp()
          }
        })
    } else {
      println(
        "update request was not executed coz the to be updatedable cache cell was not ready (updated or loaded)")
    }
    reRenderCurrentlyRoutedPageComp()

  }


  private[cache] def queReadRequest[E <: Entity](
      rr: ReadRequest[E]):Unit = readQue.queRequest(rr)


  private[this] def reRenderCurrentlyRoutedPageComp(): Unit = {
    val c: CacheMap = cache.getCacheMap()
    println("re render with cache: " + c)
    currently_routed_page.foreach({ s =>
      s.setState(c)
    })
  }

}

import slogging.LazyLogging


