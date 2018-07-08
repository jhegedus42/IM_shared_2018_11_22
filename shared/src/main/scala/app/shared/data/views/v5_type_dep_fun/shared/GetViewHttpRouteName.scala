package app.shared.data.views.v5_type_dep_fun.shared

import app.shared.data.views.v5_type_dep_fun.shared.views.View

import scala.reflect.ClassTag


case class GetViewHttpRouteName(name:String)

object GetViewHttpRouteProvider{
  def getGetViewHttpRouteName[V<:View:ClassTag]():
    GetViewHttpRouteName={
      val viewName=ViewName.getViewName[V]()
    GetViewHttpRouteName("getView_" + viewName.shortName)
  }
}

case class ViewName(fullName:String, shortName:String)

object ViewName
{
  def getViewName[V<:View:ClassTag]() : ViewName = {
    val full=implicitly[ClassTag[V]].runtimeClass.getCanonicalName
    val short=implicitly[ClassTag[V]].runtimeClass.getSimpleName
    ViewName(fullName = full,shortName = short)
  }
}
