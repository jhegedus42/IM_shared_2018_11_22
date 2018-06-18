package app.shared.data.views.v5_type_dep_fun.server.http

import app.shared.data.views.v5_type_dep_fun.server.logic.ServerSideLogic.TestTypeClass
import app.shared.data.views.v5_type_dep_fun.shared.Shared.{View, View1}
import app.shared.data.views.v5_type_dep_fun.shared.Shared.View1.{View1, View1_Res}


/**
  * Created by joco on 17/06/2018.
  */


//we want an endopoint builder -parameterized on View - that
// creates a class that contains a function f, that :

// 1) unwraps string to Par
// 2) calls the logic with Par
//    -- this logic will be provided as an implicit
// 3) wraps the result in String



object HTTP extends App{

  object Endpoint

  def g[V <: View](p: V#Par)(implicit t: TestTypeClass[V]): V#Res = {
    val r  = t.f(p)
    r
  }

  val r: View1_Res = g[View1](View1.View1_Par("234"))
  println(r)



}




