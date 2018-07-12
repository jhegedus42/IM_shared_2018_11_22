package app.client.rest.commands.generalCRUD

import app.shared.data.model.Entity.Data
import app.shared.data.model.DataType
import app.shared.rest.routes.crudCommands.CreateEntityCommCommand
import app.shared.rest.routes.crudCommands.CreateEntityCommCommand.CEC_Res
import io.circe.generic.auto._
import io.circe.parser.decode
import io.circe.syntax._
import io.circe.{Decoder, Encoder}
import org.scalajs.dom.ext.Ajax

import scala.concurrent.Future
import scala.reflect.ClassTag
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
/**
  * Created by joco on 16/12/2017.
  */
object CreateEntityAJAX{
  def createEntity[E <: Data: ClassTag: Decoder: Encoder](entity: E ): Future[CEC_Res[E]] = {

    val et:        DataType          = DataType.make[E]
    val url: String = CreateEntityCommCommand[E]().queryURL()

    val json_line: String              = entity.asJson.spaces2
    val headers:   Map[String, String] = Map( "Content-Type" -> "application/json" )

    Ajax
    .post( url, json_line, headers = headers )
    .map( _.responseText )
    .map( (x: String) => { decode[CEC_Res[E]](x) })
    .map( x => x.right.get )
  }

}
