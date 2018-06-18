package app.shared.data.views.v4_path_dep_type_based_view_architecture.shared

import app.shared.data.views.v5_type_dep_fun.shared.Shared.View
import io.circe.Decoder

import io.circe.{Decoder, Error}
import io.circe.generic.auto._

import io.circe._
import io.circe.parser._
/**
  * Created by joco on 18/06/2018.
  */
object CirceUtils {
  def decodeJSONToRes[V <: View](s: String )(implicit e: Decoder[V#Par] ): Either[Error, V#Par] = {
    val r: Either[Error, V#Par] = decode( s )
    r
  }

  def encodeResToJSON[V <: View](r: V#Res )(implicit e: Encoder[V#Res] ): String = {
    e.apply( r ).spaces4
  }

  def encodeParToJSON[V <: View](r: V#Par )(implicit e: Encoder[V#Par] ): String = {
    e.apply( r ).spaces4
  }


}
