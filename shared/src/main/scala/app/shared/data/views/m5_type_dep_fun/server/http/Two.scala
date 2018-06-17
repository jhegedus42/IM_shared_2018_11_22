package app.shared.data.views.m5_type_dep_fun.server.http

import app.shared.data.views.m5_type_dep_fun.server.logic.ServerSideLogic.TestTypeClass
import app.shared.data.views.m5_type_dep_fun.shared.Shared.{F, IntF, WrappedF}


/**
  * Created by joco on 17/06/2018.
  */



object HTTP {

  object Endpoint

  def g[V <: WrappedF](p: F[V]#Unwrap)(implicit t: TestTypeClass[V]): F[V]#Unwrap = {
    val r: F[V]#Unwrap = t.f(p)
    r
  }

  val r: String = g[IntF]("234")
  println(r)
}




