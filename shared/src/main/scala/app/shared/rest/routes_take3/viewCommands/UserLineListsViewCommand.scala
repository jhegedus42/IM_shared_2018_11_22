package app.shared.rest.routes_take3.viewCommands

import app.shared.SomeError_Trait
import app.shared.data.model.{User, UserLineList}
import app.shared.data.ref.{Ref, RefVal}
import app.shared.data.views.v2.UserLineListView
import app.shared.rest.routes_take3.Command

import scala.reflect.ClassTag
import scalaz.\/

/**
  * Created by joco on 04/01/2018.
  */
case class UserLineListsViewCommand() extends Command[UserLineList] {
  override type Params = Ref[User]
  override type Result = \/[SomeError_Trait, UserLineListView ]

  override def getServerPath = "getUserLineLists"

  def getPars(rv:  Params) = {
    val u = rv.uuid.id
    s"?id=$u"
  }

  def queryURL(rv: Params ) = "/" + getServerPath + getPars( rv )
}

object UserLineListsViewCommand {
  val gullc = UserLineListsViewCommand()
}
