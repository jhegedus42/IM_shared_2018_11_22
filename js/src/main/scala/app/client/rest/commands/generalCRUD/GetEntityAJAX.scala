package app.client.rest.commands.generalCRUD

import app.shared.model.entities.Entity.Entity
import app.shared.model.entities.{EntityType, LineText, User}
import app.shared.model.ref.{Ref, RefDyn, RefValDyn}
import app.shared.rest.routes_take3.crudCommands.GetEntityCommand
import app.shared.{SomeError_Trait, TypeError}
import io.circe.Decoder
import io.circe.generic.auto._
import io.circe.parser.decode

import scala.concurrent.Future
import scala.reflect.ClassTag
import scalaz.\/

/**
  * Created by joco on 16/12/2017.
  */
object GetEntityAJAX {


  import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
    def getEntity[E <: Entity : ClassTag : Decoder](ref: Ref[E])(implicit gec:GetEntityCommand[E]):
    Future[gec.Result] = {
      val route = gec.queryURL(ref)
//      Ajax
//      .get(route)
//      .map(_.responseText)
//      .map((x: String) => decode[gec.Result](x))
//      .map(x => x.right.get)

      GeneralGetAJAX.get[E](route, gec)(decode[GetEntityCommand[E]#Result])
    }

    // ^^^
    // 65869b2e10f94080b2e1fb350c2be50b
    //              def isRefValsTypeValid[E <: Entity](rv: RefVal[E]): Boolean = {
    //                rv.r.entityType == EntityType.make
    //              }
    //              todolater check returned RefVal's type (RefVal's validity) matches the type of E
    //              use isRefValsTypeValid

    /**
      * Uses getEntity to get an entity using only refDyn, this will be used by calls that are not type-safe,
      * where the type of the entity to be requested is not known at compile time (for example when updating the
      * cache).
      *
      * @param refDyn
      * @return
      */


    def getEntityDyn(refDyn: RefDyn): Future[ResDyn] = {

      def g[E <: Entity : ClassTag](implicit gec:GetEntityCommand[E]): gec.Result => ResDyn = {

        (result: gec.Result) => ReqResultDyn(result.map(RefValDyn.fromRefValToRefValDyn(_)))
      }

      def getCase[E <: Entity : ClassTag]: EntityType = {EntityType.make[E]}

      def res[E <: Entity : ClassTag : Decoder](refDyn: RefDyn)
                                               (implicit gec:GetEntityCommand[E]): Future[ResDyn] = {

        val refV: \/[TypeError, Ref[E]] = refDyn.toRef[E]()
        val ref: Ref[E] = refV.toEither.right.get

        (getEntity[E](ref)).
        map((x: gec.Result) => g[E](implicitly[ClassTag[E]],implicitly[GetEntityCommand[E]])(x))
      }

      import io.circe.generic.auto._
      refDyn.et match {
        case s if s == getCase[LineText] =>
          res[LineText](refDyn) // todolater abstract this more
        case s if s == getCase[User] =>
          res[User](refDyn)
      }
    }

  case class ReqResultDyn(erv: \/[SomeError_Trait, RefValDyn] )
  type ResDyn=ReqResultDyn

  }
