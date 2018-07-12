package app.shared.rest.routes.crudCommands

import app.shared.SomeError_Trait
import app.shared.data.model.Entity.Data
import app.shared.data.model.LineText
import app.shared.data.ref.{Ref, RefVal}
import app.shared.rest.routes.Command

import scala.reflect.ClassTag
import scalaz.\/

case class GetAllEntitiesCommand[E <: Data: ClassTag]() extends Command[E] {
  type Params = Unit
  type Result = \/[SomeError_Trait, List[RefVal[E]]]

  override def getServerPath = "getAll" + implicitly[ClassTag[E]].runtimeClass.getName

  def queryURL: String = "/" + getServerPath
}

object GetAllEntitiesCommand {
  implicit val gAEsLineText = new GetAllEntitiesCommand[LineText]()
}
