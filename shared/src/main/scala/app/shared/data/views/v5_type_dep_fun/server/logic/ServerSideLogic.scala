package app.shared.data.views.v5_type_dep_fun.server.logic

import app.shared.data.views.v5_type_dep_fun.shared.views.View
import app.shared.data.views.v5_type_dep_fun.shared.views.View1.{View1, View1_Par, View1_Res}


object ServerSideLogic {

  trait ServerLogicTypeClass[V <: View] {
    def getView(param: V#Par ): V#Res
  }

  implicit object ServerLogicTypeClassImpl extends ServerLogicTypeClass[View1] {
    override def getView(param: View1_Par): View1_Res = {
      View1_Res("42"+ param.s)
    }
  }



}
