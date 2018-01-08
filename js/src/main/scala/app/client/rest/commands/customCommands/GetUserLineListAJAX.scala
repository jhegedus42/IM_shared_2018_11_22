package app.client.rest.commands.customCommands

import app.client.rest.commands.generalCRUD.GeneralGetAJAX
import app.shared.data.model.{User, UserLineList}
import app.shared.data.ref.Ref
import app.shared.rest.routes_take3.entitySpecificCommands.GetUserLineListsCommand
import io.circe.generic.auto._
import io.circe.parser.decode

import scala.concurrent.Future

/**
  * Created by joco on 16/12/2017.
  */
object GetUserLineListAJAX {

  val gull= GetUserLineListsCommand.gullc

  def getUserLineLists(ref: Ref[User] ): Future[gull.Result] = {

    def route: String = gull.queryURL( ref )

    GeneralGetAJAX.get[UserLineList]( route, gull )( decode[gull.Result] )
  }

}
