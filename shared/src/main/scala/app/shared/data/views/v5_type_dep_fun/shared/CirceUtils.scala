package app.shared.data.views.v5_type_dep_fun.shared

import app.shared.data.views.v5_type_dep_fun.server.httpService.JSON
import app.shared.data.views.v5_type_dep_fun.shared.views.View
import io.circe.parser._
import io.circe.{Decoder, Error, _}
/**
  * Created by joco on 18/06/2018.
  */
object CirceUtils {
  def decodeJSONToRes[V <: View](res: JSON)(implicit e: Decoder[V#Res] ): Either[Error, V#Res] = {
    val r: Either[Error, V#Res] = decode( res.string)
    r
  }

  def decodeJSONToPar[V <: View](res: JSON)(implicit e: Decoder[V#Par] ): Either[Error, V#Par] = {
    val r: Either[Error, V#Par] = decode( res.string)
    r
  }

  def encodeResToJSON[V <: View](r: V#Res )(implicit e: Encoder[V#Res] ): String = {
    e.apply( r ).spaces4
  }

  def encodeParToJSON[V <: View](r: V#Par )(implicit e: Encoder[V#Par] ): JSON = {
    JSON ( e.apply( r ).spaces4 )
  }


}
