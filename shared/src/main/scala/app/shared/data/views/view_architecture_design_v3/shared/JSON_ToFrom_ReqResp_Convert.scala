package app.shared.data.views.view_architecture_design_v3.shared

import io.circe.Encoder
import io.circe.Encoder
import io.circe.Encoder
import io.circe.generic.auto._
import io.circe.{Decoder, Encoder}
import io.circe.Encoder
import io.circe._
import io.circe.generic.semiauto._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

/**
  * Created by joco on 02/06/2018.
  */
object JSON_ToFrom_ReqResp_Convert
{
  def encodeResponseToJSON[R<:ViewResponse[_]](response: R)(implicit encoder: Encoder[R] ): String = {
        response.asJson.spaces4
  }

  def encodeRequestToJSON[R<:ViewReqParams[_]](request: R)(implicit encoder: Encoder[R] ): String = {
    request.asJson.spaces4
  }

  def decodeJSONToRequest[V<:View,R<:ViewReqParams[V]](json: String)(implicit decoder: Decoder[R] ) : Either[Error,R] = {
    decode(json)
  }

  def decodeJSONToResponse[V<:View,R<:ViewResponse[V]](json: String)(implicit decoder: Decoder[R] ) : Either[Error,R] = {
    decode(json)
  }

//  def decodeJSONToRequest[R<:ViewReqParams[_]](json: String)(implicit decoder: Decoder[R] ) : Either[Error,R] = {
//    decode(json)
//  } // to be used by client, before sending the request over the wire

}
