package app.shared.data.views.v5_type_dep_fun.serverSide.logic

import app.shared.data.views.v5_type_dep_fun.shared.views.View
import app.shared.data.views.v5_type_dep_fun.shared.views.View1_HolderObject.{View1, View1_Par, View1_Res}
import app.shared.data.views.v5_type_dep_fun.shared.views.View2_HolderObject.{View2, View2_Par, View2_Res}


object ServerSideLogic {

  trait ServerLogicTypeClass[V <: View] {
    def getView(param: V#Par ): Option[V#Res]
  }

  implicit object ServerLogicTypeClassImplView1 extends ServerLogicTypeClass[View1] {
    override def getView(param: View1_Par): Option[View1_Res] = {
      Some(View1_Res("42"+ param.s))
    }
  }


  implicit object ServerLogicTypeClassImplView2 extends ServerLogicTypeClass[View2] {
    override def getView(param: View2_Par): Option[View2_Res] = {
      Some(View2_Res(42+ param.i))
    }
  }

}
