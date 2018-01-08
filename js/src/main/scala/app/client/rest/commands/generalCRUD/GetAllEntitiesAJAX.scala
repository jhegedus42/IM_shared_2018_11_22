package app.client.rest.commands.generalCRUD

import app.shared.model.entities.Entity.Entity
import app.shared.rest.routes_take3.crudCommands.GetAllEntitiesCommand
import io.circe.Decoder
import io.circe.generic.auto._
import io.circe.parser.decode

import scala.concurrent.Future
import scala.reflect.ClassTag

/**
  * Created by joco on 16/12/2017.
  */
object GetAllEntitiesAJAX{
  def getAllEntities[E <: Entity: ClassTag: Decoder]
    (implicit gae:GetAllEntitiesCommand[E]): Future[gae.Result] = {
    def route: String = gae.queryURL
    GeneralGetAJAX.get[E](route, gae)(decode[GetAllEntitiesCommand[E]#Result])
  }

}
