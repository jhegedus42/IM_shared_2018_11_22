package app.shared.data.views.v2

import io.circe.Encoder


import io.circe.generic.auto._
import io.circe.syntax._


object ConversionOnServerSide{

//  def payLoadToJson[V<:View,Par,Resp]
//        (pl:Resp)
//        (implicit viewAux:ViewTypeClass.Aux[Resp,Par,V],
//                  encoder:Encoder[Resp]                 ) : String =
//          {
////            viewAux.RespToJSON(pl)
//            pl.asJson.spaces4
//          }

  //  json to parameters - needed by the route handler

  //  def jsonToParameters[V<:View](v:V)(json:String)(implicit decoder:Decoder[v.Params]):
  //    Either[InvalidViewParamsError, v.Params] =  {
  //      type E=Error
  //      val decoded: Either[E, v.Params] = decode(json)
  //      Util.mapLeft(decoded, (_:E) =>InvalidViewParamsError(json))
  //  }

}

