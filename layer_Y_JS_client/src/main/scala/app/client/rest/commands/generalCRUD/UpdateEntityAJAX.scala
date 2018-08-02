package app.client.rest.commands.generalCRUD

import app.shared.data.model.Entity.Data
import app.shared.data.ref.RefVal
import app.shared.rest.routes.crudRequests.UpdateEntityRequest
import app.shared.rest.routes.crudRequests.UpdateEntityRequest.UEC_Res
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import io.circe.{Decoder, Encoder}
import org.scalajs.dom.ext.Ajax

import scala.concurrent.Future
import scala.reflect.ClassTag

object UpdateEntityAJAX {

  import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

  def updateEntity[E <: Data: ClassTag: Decoder: Encoder](
      refVal: RefVal[E]
    ): Future[UEC_Res[E]] = {
    val url:       String              = UpdateEntityRequest[E]().queryURL()
    val json_line: String              = refVal.asJson.spaces2
    val headers:   Map[String, String] = Map( "Content-Type" -> "application/json" )

    Ajax
      .put( url, json_line, headers = headers )
      .map( _.responseText )
      .map( (x: String) => decode[UEC_Res[E]]( x ) )
      .map( x => x.right.get ) //this is a bit unsafe  ...

  }
}
