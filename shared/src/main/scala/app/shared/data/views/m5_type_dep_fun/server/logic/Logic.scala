package app.shared.data.views.m5_type_dep_fun.server.logic

import app.shared.data.views.m5_type_dep_fun.shared.Shared.{F, IntF, WrappedF}

object ServerSideLogic {

  trait TestTypeClass[V <: WrappedF] {
    def f(a: F[V]#Unwrap): F[V]#Unwrap // this does not compile
  }

  implicit
  object TestTypeClassImpl extends TestTypeClass[IntF] {
    override
    def f(a: String): String = {
      val z: F[IntF]#Unwrap = "fd" + a
      z
    }
  }
  //we want an endopoint builder -parameterized on View - that
  // creates a class that contains a function f, that :

  // 1) unwraps string to Par
  // 2) calls the logic with Par
  //    -- this logic will be provided as an implicit
  // 3) wraps the result in String


}