package app.shared.data.views.v5_type_dep_fun.client

import app.shared.data.views.v5_type_dep_fun.client.ViewCacheStates.ViewCacheState
import app.shared.data.views.v5_type_dep_fun.shared.views.{View1_HolderObject, View2_HolderObject}
import app.shared.data.views.v5_type_dep_fun.shared.views.View1_HolderObject.{View1, View1_Par}
import app.shared.data.views.v5_type_dep_fun.shared.views.View2_HolderObject.{View2, View2_Par}
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

/**
  * Created by joco on 08/07/2018.
  */

case class WhatToRender(val string:String)


case class ReactComponent(cache: Cache) {



  def getWhatToRender():WhatToRender = {
    val view1Params: View1_Par = View1_HolderObject.View1_Par("hello")
    val view1CacheState: ViewCacheState[View1] =
      cache.getViewCacheState[View1_HolderObject.View1](view1Params)

    val view2Params: View2_Par = View2_HolderObject.View2_Par(137)
    val view2CacheState: ViewCacheState[View2] =
      cache.getViewCacheState[View2_HolderObject.View2](view2Params)

    val string= {
      val s1=view1CacheState.toString
      val s2=view2CacheState.toString
      val s1_ = "View1's cacheState:\n"+s1
      val s2_ = "View2's cacheState:\n"+s2
      val res= s1_ +"\n"+s2_ +"\n"
      res
    }
    WhatToRender(string)
  }
}