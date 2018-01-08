package app.shared.rest.routes_take3.entitySpecificCommands

import app.shared.SomeError_Trait
import app.shared.model.entities.{User, UserLineList}
import app.shared.model.ref.{Ref, RefVal}
import app.shared.rest.routes_take3.Command

import scala.reflect.ClassTag
import scalaz.\/

/**
  * Created by joco on 04/01/2018.
  */
case class GetUserLineListsCommand() extends Command[UserLineList] {
  override type Params = Ref[User]
  override type Result = \/[SomeError_Trait, List[RefVal[UserLineList]]]

  override def getServerPath = "getUserLineLists"

  def getPars(rv:  Params) = {
    val u = rv.uuid.id
    s"?id=$u"
  }

  def queryURL(rv: Params ) = "/" + getServerPath + getPars( rv )
}

object GetUserLineListsCommand {
  val gullc = GetUserLineListsCommand()
}
