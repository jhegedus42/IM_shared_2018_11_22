package app.shared.rest.routes_take3.crudCommands

import app.shared.SomeError_Trait
import app.shared.model.Entity.Entity
import app.shared.model.ref.RefVal
import app.shared.rest.routes_take3.Command

import scala.reflect.ClassTag
import scalaz.\/

/**
  * Created by joco on 14/12/2017.
  */


case class CreateEntityCommCommand[E<:Entity:ClassTag]() extends Command[E] {
  //  type E <:Entity
  type Params = E //uuid
  type Result = \/[SomeError_Trait,RefVal[E]]

  override def getServerPath = "create" + implicitly[ClassTag[E]].runtimeClass.getName
  // server path does not start with /

  def queryURL()= "/" + getServerPath
}

object CreateEntityCommCommand{
  type CEC_Res[E<:Entity] = CreateEntityCommCommand[E]#Result
}
