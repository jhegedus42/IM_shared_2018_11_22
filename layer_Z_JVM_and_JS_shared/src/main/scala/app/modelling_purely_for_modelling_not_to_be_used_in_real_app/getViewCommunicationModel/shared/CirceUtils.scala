package app.modelling_purely_for_modelling_not_to_be_used_in_real_app.getViewCommunicationModel.shared

import app.modelling_purely_for_modelling_not_to_be_used_in_real_app.getViewCommunicationModel.serverSide.akkaHttpWebServer.JSONContainingGetViewPar
import app.modelling_purely_for_modelling_not_to_be_used_in_real_app.getViewCommunicationModel.shared.views.View
import io.circe.parser._
import io.circe.{Decoder, Error, _}

/**
  * Created by joco on 18/06/2018.
  */
object CirceUtils {

  case class JSONContainingOptRes(string: String )

  def decodeJSONContainingOptResToOptRes[V <: View](
      res: JSONContainingOptRes
    )(
      implicit
      e: Decoder[V#Res]
    ): Either[Error, V#Res] = {
    val r: Either[Error, V#Res] = decode( res.string )
    r
  }

  def decodeJSONToPar[V <: View](
      res: JSONContainingGetViewPar
    )(
      implicit
      e: Decoder[V#Par]
    ): Either[Error, V#Par] = {
//    println("decodeJSONToPar is called, with parameter:"+res)
    val r: Either[Error, V#Par] = decode( res.string )
    r
  }

  def encodeOptResToJSONContainingOptRes[V <: View](
      r: Option[V#Res]
    )(
      implicit
      e: Encoder[Option[V#Res]]
    ): JSONContainingOptRes = {
    JSONContainingOptRes( e.apply( r ).spaces4 )
  }

  def encodeParToJSON[V <: View](r: V#Par )(implicit e: Encoder[V#Par] ): JSONContainingGetViewPar = {
    JSONContainingGetViewPar( e.apply( r ).spaces4 )
  }

}