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
object JSONConvert
{
  def encodeResponseToJSON[R<:ViewResponse[_]](response: R)(implicit encoder: Encoder[R] ): String = {
        response.asJson.spaces4
  } //to be used by server, before sending the response over the wire

  def encodeRequestToJSON[R<:ViewReqParams[_]](response: R)(implicit encoder: Encoder[R] ): String = {
        response.asJson.spaces4
  } //to be used by client, before sending the request over the wire


}
