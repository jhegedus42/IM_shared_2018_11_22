package app.shared.data.views.v5_type_dep_fun.shared

import app.shared.data.views.v5_type_dep_fun.shared.views.View

import scala.reflect.ClassTag


case class HttpRouteName(name:String)

object GetViewHttpRouteName{
  def getViewHttpRouteName[V<:View:ClassTag]():
    HttpRouteName={
      val viewName=ViewName.getViewName[V]()
    HttpRouteName("getView_" + viewName.shortName)
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
