package app.shared.rest.routes_take3.crudCommands

/**
  * Created by joco on 17/12/2017.
  */


import app.shared.SomeError_Trait
import app.shared.model.Entity.Entity
import app.shared.model.ref.RefVal
import app.shared.rest.routes_take3.Command

import scala.reflect.ClassTag
import scalaz.\/

/**
  * Created by joco on 14/12/2017.
  */


case class UpdateEntityCommCommand[E<:Entity:ClassTag]() extends Command[E] {
  //  type E <:Entity
  type Params = RefVal[E] //uuid
  type Result = \/[SomeError_Trait,RefVal[E]]

  override def getServerPath = "update" + implicitly[ClassTag[E]].runtimeClass.getName
  // server path does not start with /

  def queryURL()= "/" + getServerPath
}

object UpdateEntityCommCommand{
  type UEC_Res[E<:Entity] = UpdateEntityCommCommand[E]#Result
  type UEC_Par[E<:Entity] = UpdateEntityCommCommand[E]#Params
}
