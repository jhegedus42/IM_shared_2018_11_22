package app.shared.data.views.m5_type_dep_fun.server.logic

import app.shared.data.views.m5_type_dep_fun.shared.Shared.{IntF, Lifted, Par, Res, View, View1}

object ServerSideLogic {

  trait TestTypeClass[V <: Lifted with View] {
    def f(a: Par[V]#Unwrap ): Res[V]#Unwrap // this does not compile
  }

  implicit object TestTypeClassImpl extends TestTypeClass[View1] {
    override def f(a: String ): Int = {
      42+a.length
    }
  }



}
