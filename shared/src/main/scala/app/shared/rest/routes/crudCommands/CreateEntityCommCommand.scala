package app.shared.rest.routes.crudCommands

import app.shared.SomeError_Trait
import app.shared.data.model.Entity.Data
import app.shared.data.ref.RefVal
import app.shared.rest.routes.Command

import scala.reflect.ClassTag
import scalaz.\/

/**
  * Created by joco on 14/12/2017.
  */


case class CreateEntityCommCommand[E<:Data:ClassTag]() extends Command[E] {
  //  type E <:Entity
  type Params = E //uuid
  type Result = \/[SomeError_Trait,RefVal[E]]

  override def getServerPath = "create" + implicitly[ClassTag[E]].runtimeClass.getName
  // server path does not start with /

  def queryURL()= "/" + getServerPath
}

object CreateEntityCommCommand{
  type CEC_Res[E<:Data] = CreateEntityCommCommand[E]#Result
}