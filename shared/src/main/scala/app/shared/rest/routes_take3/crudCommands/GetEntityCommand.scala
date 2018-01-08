package app.shared.rest.routes_take3.crudCommands

import app.shared.SomeError_Trait
import app.shared.data.model.Entity.Data
import app.shared.data.model.{LineText, User}
import app.shared.data.ref.{Ref, RefVal}
import app.shared.rest.routes_take3.Command

import scala.reflect.ClassTag
import scalaz.\/

/**
  * Created by joco on 14/12/2017.
  */
case class GetEntityCommand[E<:Data:ClassTag]() extends Command[E] {
//  type E <:Entity
  type Params = String //uuid
  type Result = \/[SomeError_Trait,RefVal[E]]

  override def getServerPath = "getSingleEntity" + implicitly[ClassTag[E]].runtimeClass.getName
  // server path does not start with /


  def getPars(rv:Ref[E])= {
    val u =rv.uuid.id
    s"?id=$u"

  }
  def queryURL(rv:Ref[E])= "/" + getServerPath + getPars(rv)
}

object GetEntityCommand{
  implicit val geLT=new GetEntityCommand[LineText]()
  implicit val geUser=new GetEntityCommand[User]()
}