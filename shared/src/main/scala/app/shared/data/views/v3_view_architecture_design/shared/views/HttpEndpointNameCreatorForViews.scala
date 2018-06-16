package app.shared.data.views.v3_view_architecture_design.shared.views

import app.shared.data.views.v3_view_architecture_design.shared.views.common.{View, ViewName}

import scala.reflect.ClassTag

/**
  * Created by joco on 03/06/2018.
  */
case class HttpEndpointName(name:String)

object HttpEndpointNameCreatorForViews {
  def getViewHttpEndpointName[V<:View:ClassTag]():HttpEndpointName={
    val viewName=ViewName.getViewName[V]()
    HttpEndpointName("getView_"+viewName.shortName)
  }
}
