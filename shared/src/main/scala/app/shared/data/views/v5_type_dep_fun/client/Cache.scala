package app.shared.data.views.v5_type_dep_fun.client

import ViewCacheStates.ViewCacheState
import app.shared.data.views.v5_type_dep_fun.shared.views.{Parameter, View}


class Cache{

  var map=Map[Parameter, ViewCacheState[_]]()

  def getViewCacheState[V<:View](p:V#Par): ViewCacheState[V]= {
    val cs: Option[ViewCacheState[_]] = map.get(p)
    cs.asInstanceOf[ViewCacheState[V]]

    // todo - continue here

  }

}


