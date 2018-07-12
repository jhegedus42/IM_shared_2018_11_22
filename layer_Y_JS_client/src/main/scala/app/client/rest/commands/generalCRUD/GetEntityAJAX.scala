package app.client.rest.commands.generalCRUD

import app.shared.data.model.Entity.Data
import app.shared.data.model.{DataType, LineText, User}
import app.shared.data.ref.{Ref, RefDyn, RefValDyn}
import app.shared.rest.routes.crudCommands.GetEntityCommand
import app.shared.{SomeError_Trait, TypeError}
import io.circe.Decoder
import io.circe.generic.auto._
import io.circe.parser.decode

import scala.concurrent.Future
import scala.reflect.ClassTag
import scalaz.\/

object GetEntityAJAX {


  import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

    def getEntity[E <: Data : ClassTag : Decoder](ref: Ref[E])(implicit gec:GetEntityCommand[E]):
    Future[gec.Result] = {
      val route: String = gec.queryURL(ref)
      GeneralGetAJAX.get[E](route, gec)(decode[GetEntityCommand[E]#Result])
    }


    /**
      * Uses getEntity to get an entity using only refDyn, this will be used by calls that are not type-safe,
      * where the type of the entity to be requested is not known at compile time (for example when updating the
      * cache).
      *
      * @param refDyn
      * @return
      */
    def getEntityDyn(refDyn: RefDyn): Future[ResDyn] = {

      def g[E <: Data : ClassTag](implicit gec:GetEntityCommand[E]): gec.Result => ResDyn = {

        (result: gec.Result) => ReqResultDyn(result.map(RefValDyn.fromRefValToRefValDyn(_)))
      }

      def getCase[E <: Data : ClassTag]: DataType = {DataType.make[E]}

      def res[E <: Data : ClassTag : Decoder](refDyn: RefDyn)
                                             (implicit gec:GetEntityCommand[E]): Future[ResDyn] = {

        val refV: \/[TypeError, Ref[E]] = refDyn.toRef[E]()
        val ref: Ref[E] = refV.toEither.right.get

        (getEntity[E](ref)).
        map((x: gec.Result) => g[E](implicitly[ClassTag[E]],implicitly[GetEntityCommand[E]])(x))
      }

      import io.circe.generic.auto._
      refDyn.et match {
        case s if s == getCase[LineText] =>
          res[LineText](refDyn)
        case s if s == getCase[User] =>
          res[User](refDyn)
      }
    }

  case class ReqResultDyn(erv: \/[SomeError_Trait, RefValDyn] )
  type ResDyn=ReqResultDyn

  }
