package app.shared.data.views.v5_type_dep_fun.client

import ViewCacheStates.{Loaded, LoadingCacheState, ViewCacheState}
import app.shared.data.views.v5_type_dep_fun.shared.views.{Parameter, Result, View}
import io.circe.{Decoder, Encoder}

import scala.concurrent.Future
import scala.reflect.ClassTag
import scala.util.{Failure, Success, Try}
//import scala.concurrent
import scala.concurrent.ExecutionContext.Implicits.global
//import app.shared.data.views.v5_type_dep_fun.ExecutionContexts.singleThreadedExecutionContext

case class Cache(ajaxInterface: AjaxInterface, pendingGetViewAjaxRequests: PendingGetViewAjaxRequests ) {

  var map = Map[Parameter, ViewCacheState[_]]()

  def getViewCacheState[V <: View: ClassTag: Encoder](
      params: V#Par
    )(
      implicit
      e: Encoder[V#Par],
      d: Decoder[V#Res]
    ): ViewCacheState[V] = {

    val cacheStateOption: Option[ViewCacheState[_]] = map.get( params )

    val viewCacheState: ViewCacheState[V] = cacheStateOption match {
      case None => {

        val loadingViewCacheState: ViewCacheState[V] = LoadingCacheState( params )

        val newMap: Map[Parameter, ViewCacheState[_]] = map.updated( params, loadingViewCacheState )
        map = newMap

        val ajaxGetViewReq: GetViewAjaxRequest[V] = ajaxInterface.getAJAXGetViewRequest[V]( params ) // 2

        pendingGetViewAjaxRequests.addPendingAJAXRequest( ajaxGetViewReq )

        def handleOnComplete(tryOptRes: Try[Option[V#Res]] ): Unit = {
          println( "An GetViewAjaxRequest has completed, it returned with:" + tryOptRes )

          val res: Option[V#Res] = tryOptRes match {
            case Failure( exception ) => None
            case Success( value )     => value
          }

          res match {
            case Some( x ) => {
              val loaded = Loaded[V]( x )
              val newMap: Map[Parameter, ViewCacheState[_]] = map.updated( params, loaded )
              map = newMap
              // this is OK in JS - it is single threaded
              // also here we are using a single threaded execution context
            }
            case None => Unit
          }

          pendingGetViewAjaxRequests.handleGetViewAjaxRequestCompleted( ajaxGetViewReq ) // 4

          println("just before registering the handleOnComplete on the future")
          ajaxGetViewReq.ajaxResFuture.onComplete( {
            (x: Try[Option[V#Res]]) => {
              println("ajaxGetViewReq.ajaxResFuture's completed")
              handleOnComplete(_)
            }
          } ) // 5
        }
        loadingViewCacheState
      }

      case Some( vcs ) => {

        // todo some logic is needed to handle the Invalidated state

        val res: ViewCacheState[V] = vcs.asInstanceOf[ViewCacheState[V]]

        val castedRes = res.asInstanceOf[V#Res]
        Loaded( castedRes )
      }

    }
    viewCacheState
  }

}
