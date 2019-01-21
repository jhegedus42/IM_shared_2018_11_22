//package app.client.rest.commands.generalCRUD
//
//import app.shared.data.model.Entity.Entity
//import app.shared.rest.routes.crudRequests.GetAllEntitiesRequest
//import io.circe.Decoder
//import io.circe.generic.auto._
//import io.circe.parser.decode
//
//import scala.concurrent.Future
//import scala.reflect.ClassTag
//
///**
//  * Created by joco on 16/12/2017.
//  */
//object GetAllEntitiesAJAX{
//  def getAllEntities[E <: Entity: ClassTag: Decoder]
//    (implicit gae:GetAllEntitiesRequest[E]): Future[gae.Result] = {
//    def route: String = gae.queryURL
//    GeneralGetAJAX.get[E](route, gae)(decode[GetAllEntitiesRequest[E]#Result])
//  }
//
//}
