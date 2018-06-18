package app.shared.data.views.v5_type_dep_fun.server.http

import app.shared.data.views.v5_type_dep_fun.server.logic.ServerSideLogic.ServerLogicTypeClass
import app.shared.data.views.v5_type_dep_fun.shared.Shared.{View, View1}
import io.circe.{Encoder, Json}
import io.circe.parser._
import io.circe.syntax._
import app.shared.data.views.v4_path_dep_type_based_view_architecture.shared.CirceUtils._
import app.shared.data.views.v5_type_dep_fun.shared.Shared.View1.{View1, View1_Par}
import io.circe.{Decoder, Error}


import io.circe.Decoder

import io.circe.{Decoder, Error}
import io.circe.generic.auto._

import io.circe._
import io.circe.parser._

case class JSON(s: String )

object HTTP extends App {}

object EndpointCreator extends App {

  //we want an endopoint builder -parameterized on View - that
  // creates a class that contains a function f, that :

  def f[V <: View](
      json: JSON
    )(
      implicit
      decoder: Decoder[V#Par],
      encoder: Encoder[V#Res],
      serverLogic:ServerLogicTypeClass[V]
    ): String = {

    val r:   Either[Error, V#Par] = decodeJSONToRes[V]( json.s )

    val par: V#Par                = r.right.get

    val res: V#Res = serverLogic.f(par )

    encodeResToJSON[V](res)
  }

  val par: View1_Par = View1.View1_Par("pina")

  val par_json: String = encodeParToJSON[View1](par)

  val r: String = f[View1](JSON(par_json))

  println(r)

}
