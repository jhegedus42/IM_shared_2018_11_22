package app.shared.rest.routes.crudCommands

import app.shared.SomeError_Trait
import app.shared.data.model.Entity.Data
import app.shared.data.model.{LineText, User}
import app.shared.data.ref.{Ref, RefVal}
import app.shared.rest.routes.Command

import scala.reflect.ClassTag
import scalaz.\/

/**
  * Documentation should go here.
  */
case class GetEntityCommand[E<:Data:ClassTag]() extends Command[E] {
  type Params = String //uuid
  type Result = \/[SomeError_Trait,RefVal[E]]

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

object GetEntityCommand{
  implicit val geLT  : GetEntityCommand[LineText] =new GetEntityCommand[LineText]()
  implicit val geUser: GetEntityCommand[User]     =new GetEntityCommand[User]()
}