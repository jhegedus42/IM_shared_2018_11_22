package app.shared.rest.routes_take3.crudCommands

import app.shared.SomeError_Trait
import app.shared.model.entities.Entity.Entity
import app.shared.model.entities.LineText
import app.shared.model.ref.{Ref, RefVal}
import app.shared.rest.routes_take3.Command

import scala.reflect.ClassTag
import scalaz.\/

/**
  * Created by joco on 14/12/2017.
  */
// ====> 1.3.1.1 <====  task-completed 1.3.1.1 GetAllEntitiesCommand - COMPLETED
case class GetAllEntitiesCommand[E <: Entity: ClassTag]() extends Command[E] {
  //  type E <:Entity
  type Params = Unit //uuid
  type Result = \/[SomeError_Trait, List[RefVal[E]]]

  override def getServerPath = "getAll" + implicitly[ClassTag[E]].runtimeClass.getName
  // server path does not start with /

  def queryURL: String = "/" + getServerPath
}

object GetAllEntitiesCommand {
  implicit val gAEsLineText = new GetAllEntitiesCommand[LineText]()
}
