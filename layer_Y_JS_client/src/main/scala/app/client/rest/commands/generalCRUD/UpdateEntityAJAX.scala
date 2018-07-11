/**
  * Created by joco on 11/05/2017.
  */
package app.client.rest.commands.generalCRUD

import app.shared.data.model.Entity.Data
import app.shared.data.ref.RefVal
import app.shared.rest.routes.crudCommands.UpdateEntityCommCommand
import app.shared.rest.routes.crudCommands.UpdateEntityCommCommand.UEC_Res
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import io.circe.{Decoder, Encoder}
import org.scalajs.dom.ext.Ajax

import scala.concurrent.Future
import scala.reflect.ClassTag

object UpdateEntityAJAX {

  import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
//  19ccf04fc4f54cce84344f0216c6505a commit e3e59ee5d121ea9c389f1f93fcfdc7192cacaeb4 Sun Dec 17 17:48:13 EET 2017

  def updateEntity[E <: Data: ClassTag: Decoder: Encoder](
      refVal: RefVal[E]
    ): Future[UEC_Res[E]] = {
    val url:       String              = UpdateEntityCommCommand[E]().queryURL()
    val json_line: String              = refVal.asJson.spaces2
    val headers:   Map[String, String] = Map( "Content-Type" -> "application/json" )

    Ajax
      .put( url, json_line, headers = headers )
      .map( _.responseText )
      .map( (x: String) => decode[UEC_Res[E]]( x ) )
      .map( x => x.right.get ) //this is a bit unsafe  ...

  }
  // daa13758b1304ea0a77edb1979eea8b9
  // ^^^ todolater, get rid of this unsafe .right.get
  // flatten these nested Eithers - if the ajax call gets fucked up, merge that into the
  // returned Either Type...

  //      def isRefValsTypeValid[E <: Entity](rv: RefVal[E]): Boolean = {
  //        rv.r.entityType == EntityType.make
  //      }
  // todolater check returned RefVal's type (RefVal's validity) matches the type of E
  // use isRefValsTypeValid
}
//trait CRUDRouteClientSide extends CRUDRouteShared {
//  type ResDyn
//}
