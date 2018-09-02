package app.comm_model_on_the_server_side.serverSide.logic

import app.copy_of_model_to_be_moved_to_real_app.getViewCommunicationModel.shared.views.View
import app.copy_of_model_to_be_moved_to_real_app.getViewCommunicationModel.shared.views.View1_HolderObject.{View1, View1_Par, View1_Res}
import app.copy_of_model_to_be_moved_to_real_app.getViewCommunicationModel.shared.views.View2_HolderObject.{View2, View2_Par, View2_Res}


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
