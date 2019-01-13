package app.shared.rest.routes.crudRequests

import app.shared.SomeError_Trait
import app.shared.data.model.Entity.{Data, Entity}
import app.shared.data.model.{LineText, User}
import app.shared.data.ref.{Ref, RefVal}
import app.shared.rest.routes.Command

import scala.reflect.ClassTag
import scalaz.\/




case class GetEntityRequest[E<:Entity:ClassTag]() extends Command[E] {
  type Params = String //uuid

  case class GetEntityReqResult(res: \/[SomeError_Trait,RefVal[E]])

  /**
    * server path does not start with /
    * @return
    */
  override def getServerPath: String = "getSingleEntity" + implicitly[ClassTag[E]].runtimeClass.getName

  /**
    *
    * @param rv
    * @return
    */
  def getPars(rv:Ref[E]): String = {
    val u =rv.uuid.id
    s"?id=$u"
  }

  /**
    *
    * @param rv
    * @return
    */
  def queryURL(rv:Ref[E]): String = "/" + getServerPath + getPars(rv)
}

object GetEntityRequest{
  implicit val geLT  : GetEntityRequest[LineText] =new GetEntityRequest[LineText]()
  implicit val geUser: GetEntityRequest[User]     =new GetEntityRequest[User]()
}