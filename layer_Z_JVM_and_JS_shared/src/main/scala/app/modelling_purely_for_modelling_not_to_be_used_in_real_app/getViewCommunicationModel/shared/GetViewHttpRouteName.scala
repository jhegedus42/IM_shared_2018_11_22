package app.modelling_purely_for_modelling_not_to_be_used_in_real_app.getViewCommunicationModel.shared

import app.modelling_purely_for_modelling_not_to_be_used_in_real_app.getViewCommunicationModel.shared.views.View

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