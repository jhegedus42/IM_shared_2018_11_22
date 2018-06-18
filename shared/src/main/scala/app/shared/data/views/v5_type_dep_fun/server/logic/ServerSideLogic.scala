package app.shared.data.views.v5_type_dep_fun.server.logic

import app.shared.data.views.v5_type_dep_fun.shared.Shared.View1.{View1, View1_Par, View1_Res}
import app.shared.data.views.v5_type_dep_fun.shared.Shared.{ View, View1}

object ServerSideLogic {

  trait TestTypeClass[V <: View with View] {
//    def f(a: Par[V] ): Res[V]
    def f(a: V#Par ): V#Res
  }

  implicit object TestTypeClassImpl extends TestTypeClass[View1] {
    override def f(a: View1_Par): View1_Res = {
      View1_Res(42+a.s.length)
    }
  }



}
