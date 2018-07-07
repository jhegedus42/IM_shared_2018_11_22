package app.shared.data.views.v5_type_dep_fun.client

import ViewCacheStates.{Loaded, ViewCacheState}
import app.shared.data.views.v5_type_dep_fun.shared.views.{Parameter, View}

import scala.concurrent.Future
import scala.util.Try


case class Cache(ajaxInterface: AjaxInterface){

  var map=Map[Parameter, ViewCacheState[_]]()

  def getViewCacheState[V<:View](params:V#Par): ViewCacheState[V]= {

    val cacheStateOption: Option[ViewCacheState[_]] = map.get(params)

    val result : ViewCacheState[V] = cacheStateOption match {
      case None => {

        val loadingViewCacheState:ViewCacheState[V] = ???
        val newMap: Map[Parameter, ViewCacheState[_]] = map.updated(params, loadingViewCacheState)
        map=newMap

//        val future: Future[Option[V#Res]] = ajaxInterface.getView(params)

//        future.onComplete(
//                           (res: Try[Option[V#Res]]) => {
//                              if(res.isSuccess){
//                                val resOpt: Option[V#Res] = res.get
//                                if(resOpt.isDefined) {
//                                  val resFullSuccess: V#Res = resOpt.head
//                                  val newMap: Map[Parameter, ViewCacheState[_]] =
//                                    map.updated(params, Loaded(resFullSuccess))
//                                  map=newMap
//                                  // notify React Render Engine that it should re-render stuff
//                                  // this is a bad idea !!!
//                                }
//                              }
//                           }
//                         )
//


        loadingViewCacheState
      }
      case Some(vcs) => vcs.asInstanceOf[ViewCacheState[V]]
    }
    result
  }

}


