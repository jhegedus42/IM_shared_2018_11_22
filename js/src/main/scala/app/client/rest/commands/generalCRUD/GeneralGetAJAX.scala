package app.client.rest.commands.generalCRUD

import app.shared.model.entities.Entity.Entity
import app.shared.rest.routes_take3.Command
import io.circe.Decoder
import org.scalajs.dom.ext.Ajax

import scala.concurrent.Future
import scala.reflect.ClassTag

/**
  * Created by joco on 16/12/2017.
  */
object GeneralGetAJAX {
  import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

  def get[E <: Entity: ClassTag: Decoder](
      route:   String,
      c:       Command[E]
    )(decoder: String => Either[io.circe.Error, c.Result]
    ): Future[c.Result] =
    Ajax
      .get( route )
      .map( _.responseText )
      .map( decoder( _ ) )
      .map( x => x.right.get )
}
