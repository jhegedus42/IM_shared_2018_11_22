package app.client.wrapper._wrapper_reqHandlers

import app.client.rest.commands.generalCRUD.GetEntityAJAX
import app.client.rest.commands.generalCRUD.GetEntityAJAX.ResDyn
import app.client.wrapper.wrapperFactory._wrapper_EntityCache_MutableState
import app.client.wrapper.wrapperFactory.wrapperFactoryClass.ReadRequest
import app.client.wrapper.{Loading, NotYetLoaded}
import app.shared.SomeError_Trait
import app.shared.data.model.Entity.Entity
import app.shared.data.ref.RefVal
import scalaz.{-\/, \/-}
import slogging.LazyLogging

import scala.concurrent.Future

/**
  * Created by joco on 29/12/2017.
  */
private[wrapper] class ReadReqHandler(cache: _wrapper_EntityCache_MutableState, pageRerenderer: () => Unit)
    extends LazyLogging {

  private[this] var readRequests: Set[ReadRequest[_ <: Entity]] = Set()
  private[wrapper] def queRequest[E <: Entity](rr: ReadRequest[E]): Unit = {
    readRequests = readRequests + rr
    logger.trace("in collect read requests - readRequests:" + rr)
  }

  private[wrapper] def executeReadRequests(): Unit = {
    logger.trace("in executeReadRequests read requests:" + readRequests)
    println("137" + readRequests)

    def processRequest[E <: Entity]: (ReadRequest[E]) => Future[Unit] = {
      (rr: ReadRequest[E]) =>
        {
          logger.trace("...")
          println("processing read request " + rr)
          import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

          val notYetLoaded: NotYetLoaded[E] =
            cache.EntityStateChanger.setNotYetLoaded(rr.ref)
          // start Future

          val res: Future[ResDyn] = GetEntityAJAX.getEntityDyn(rr.ref)

          val loading: Loading[E] = cache.EntityStateChanger.setLoading(notYetLoaded)

          res.map({ r: ResDyn =>
            println(s"future completed for $rr, result is $r")
            val res: Unit = r.erv match {
              case -\/(err) =>
                cache.EntityStateChanger.setReadFailed[E](loading, err.toString)
              case \/-(refValDyn) =>
                refValDyn.toRefVal_NoClassTagNeeded(rr.ref.dataType) match {
                  case -\/(a: SomeError_Trait) =>
                    cache.EntityStateChanger.setReadFailed(oldVal = loading,
                                               errorMsg = a.string)

                  case \/-(b: RefVal[E]) =>
                    cache.EntityStateChanger.setLoaded[E](oldVal = loading, newVal = b)
                }
            }
            pageRerenderer() // we rerender the page after each ajax came back
          })
          //637e3709d2fa4bd19e9167d45b58c425 commit b644e0744804cc562d4c7648aafaae93ec4727e5 Mon Dec 18 00:18:55 EET 2017
        }
    }

    readRequests.foreach(
      (x: ReadRequest[_ <: Entity]) => {

        processRequest(x) //we launch an AJAX call for each entity to be read
      }
    )

    readRequests = Set()
    pageRerenderer() // we rerender the page after each ajax req has been started
  }

}
