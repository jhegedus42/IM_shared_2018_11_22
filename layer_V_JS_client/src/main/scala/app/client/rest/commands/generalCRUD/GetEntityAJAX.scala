package app.client.rest.commands.generalCRUD

import app.shared.data.model.Entity.{Data, Entity}
import app.shared.data.model.{DataType, LineText, User}
import app.shared.data.ref.{Ref, RefDyn, RefVal, RefValDyn}
import app.shared.rest.routes.crudRequests.GetEntityRequest
import app.shared.rest.routes.crudRequests.GetEntityRequest.GetEntityReqResult
import app.shared.{SomeError_Trait, TypeError}
import io.circe
import io.circe.Decoder
import io.circe.generic.auto._
import io.circe.parser.decode
import org.scalajs.dom.ext.Ajax

import scala.concurrent.Future
import scala.reflect.ClassTag
import scalaz.\/

object GetEntityAJAX {


  import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

  def getEntity[E <: Entity : ClassTag ](ref: Ref[E])(implicit d:Decoder[RefVal[E]]):
  Future[GetEntityReqResult[E]] = {
    import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
    val route: String =GetEntityRequest.queryURL(ref)
//    val route: String = ???
    Ajax
    .get( route )
    .map( _.responseText )
    .map( decode )
    .map((x: Either[circe.Error, RefVal[E]) => x.right.get)
    .map( x:RefVal[E] => GetEntityReqResult )
  }

}

