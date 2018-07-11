package app.shared.rest.routes.crudCommands

/**
  * Created by joco on 17/12/2017.
  */


import app.shared.SomeError_Trait
import app.shared.data.model.Entity.Data
import app.shared.data.ref.RefVal
import app.shared.rest.routes.Command

import scala.reflect.ClassTag
import scalaz.\/

/**
  * Created by joco on 14/12/2017.
  */


case class UpdateEntityCommCommand[E<:Data:ClassTag]() extends Command[E] {
  //  type E <:Entity
  type Params = RefVal[E] //uuid
  type Result = \/[SomeError_Trait,RefVal[E]]

  override def getServerPath = "update" + implicitly[ClassTag[E]].runtimeClass.getName
  // server path does not start with /

  def queryURL()= "/" + getServerPath
}

object UpdateEntityCommCommand{
  type UEC_Res[E<:Data] = UpdateEntityCommCommand[E]#Result
  type UEC_Par[E<:Data] = UpdateEntityCommCommand[E]#Params
}
