package app.shared.data.views.m5_type_dep_fun.server.http

import app.shared.data.views.m5_type_dep_fun.server.logic.ServerSideLogic.TestTypeClass
import app.shared.data.views.m5_type_dep_fun.shared.Shared.{ IntF, Lifted}


/**
  * Created by joco on 17/06/2018.
  */


//we want an endopoint builder -parameterized on View - that
// creates a class that contains a function f, that :

// 1) unwraps string to Par
// 2) calls the logic with Par
//    -- this logic will be provided as an implicit
// 3) wraps the result in String



object HTTP {

  object Endpoint

//  def g[V <: Lifted](p: F[V]#Unwrap)(implicit t: TestTypeClass[V]): F[V]#Unwrap = {
//    val r: F[V]#Unwrap = t.f(p)
//    r
//  }
//
//  val r: String = g[IntF]("234")
//  println(r)
}




