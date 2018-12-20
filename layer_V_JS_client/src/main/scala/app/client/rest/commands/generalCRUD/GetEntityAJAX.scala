package app.client.rest.commands.generalCRUD

import app.shared.data.model.Entity.{Data, Entity}
import app.shared.data.model.{DataType, LineText, User}
import app.shared.data.ref.{Ref, RefDyn, RefValDyn}
import app.shared.rest.routes.crudRequests.GetEntityRequest
import app.shared.{SomeError_Trait, TypeError}
import io.circe.Decoder
import io.circe.generic.auto._
import io.circe.parser.decode

import scala.concurrent.Future
import scala.reflect.ClassTag
import scalaz.\/

object GetEntityAJAX {


  import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

  def getEntity[E <: Entity : ClassTag : Decoder](ref: Ref[E])(implicit gec: GetEntityRequest[E]):
  Future[gec.Result] = {
    val route: String = gec.queryURL(ref)
    GeneralGetAJAX.get[E](route, gec)(decode[GetEntityRequest[E]#Result])
  }

}

