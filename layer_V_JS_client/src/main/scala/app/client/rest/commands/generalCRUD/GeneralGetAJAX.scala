package app.client.rest.commands.generalCRUD
//
//import app.shared.data.model.Entity.Data
//import app.shared.rest.routes.Command
//import io.circe.Decoder
//import org.scalajs.dom.ext.Ajax
//
//import scala.concurrent.Future
//import scala.reflect.ClassTag
//
///**
//  * Created by joco on 16/12/2017.
//  */
//object GeneralGetAJAX {
//  import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
//
//  def get[E <: Data: ClassTag: Decoder](
//      route:   String,
//      c:       Command[E]
//    )(decoder: String => Either[io.circe.Error, c.Result]
//    ): Future[c.Result] = ???
//}
