package app.shared.data.views.v3_view_architecture_design.shared.views.common

import scala.reflect.ClassTag

/**
  * Created by joco on 02/06/2018.
  */
case class ViewName(fullName:String, shortName:String)

object ViewName
{
     def getViewName[V<:View:ClassTag]() : ViewName = {
       val full=implicitly[ClassTag[V]].runtimeClass.getCanonicalName
       val short=implicitly[ClassTag[V]].runtimeClass.getSimpleName
       ViewName(fullName = full,shortName = short)
     }
}


