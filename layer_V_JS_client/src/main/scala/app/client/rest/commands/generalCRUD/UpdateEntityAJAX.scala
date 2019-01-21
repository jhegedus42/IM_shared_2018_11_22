//package app.client.rest.commands.generalCRUD
//
//import app.shared.data.model.Entity.{Data, Entity}
//import app.shared.data.ref.RefVal
//import app.shared.rest.routes.crudRequests.UpdateEntityRequest
//import app.shared.rest.routes.crudRequests.UpdateEntityRequest.UpdateEntityRequestResult
//import io.circe.generic.auto._
//import io.circe.parser._
//import io.circe.syntax._
//import io.circe.{Decoder, Encoder}
//import org.scalajs.dom.ext.Ajax
//
//import scala.concurrent.Future
//import scala.reflect.ClassTag
//
//
//object UpdateEntityAJAX {
//
//  import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
//
//  /**
//    *
//    * Sends a request to the server to update an Entity.
//    *
//    * @param refVal
//    * @tparam E
//    * @return Future that contains the result of the update request.
//    */
//
//  def updateEntity[E <: Entity: ClassTag: Decoder: Encoder]
//      (refVal: RefVal[E]): Future[UpdateEntityRequestResult[E]] =
//
//  {
//    val url:       String              = UpdateEntityRequest[E]().queryURL()
//    val json_line: String              = refVal.asJson.spaces2
//    val headers:   Map[String, String] = Map( "Content-Type" -> "application/json" )
//
//    Ajax
//      .put( url, json_line, headers = headers )
//      .map( _.responseText )
//      .map( (x: String) => decode[UpdateEntityRequestResult[E]](x))
//      .map( x => x.right.get ) // this is a bit unsafe  ...
//                               // fixme one day, since then let's hope that we circe is not buggy
//                               // or I cannot even thing of a situation where this could go wrong
//                               // or what could make this go wrong ? ---> maybe later, aligator
//    // Random UUID: 71175cc9d8564d5b8722a76cbb620e76
//    // commit 261bba625a6dc3bfc178a1d578cd104b23cf6437
//    // Date: Wed Aug  8 09:03:36 EEST 2018
//
//
//  }
//}
