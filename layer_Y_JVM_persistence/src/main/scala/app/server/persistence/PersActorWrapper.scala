package app.server.persistence

import akka.actor.ActorRef
import akka.util.Timeout
import app.server.persistence.persActor.Commands.{CreateEntityPACommand, CreateEntityPAResponse, GetStatePACommand, GetStatePAResponse, SetStatePACommand, SetStatePAResponse, UpdateEntityPACommand, UpdateEntityPAResponse}

import app.shared.data.model.Entity.Data
import app.shared.data.ref.RefValDyn
import app.testHelpersShared.data.TestDataLabels.TestDataLabel
import app.testHelpersShared.implicits.ForTestingOnly

import scala.concurrent.Future


/**
  * Created by joco on 09/10/2017.
  */
trait PersActorWrapperIF{
  def getState: Future[GetStatePAResponse]

  def updateEntity(rfvd: RefValDyn): Future[UpdateEntityPAResponse]

  def createEntity(e:Data):Future[CreateEntityPAResponse]

  def setState(s:TestDataLabel): Future[SetStatePAResponse]
}

class PersActorWrapper(private[this] val actor: ActorRef) extends PersActorWrapperIF{
  import akka.pattern.ask
  import akka.util.Timeout

  import scala.concurrent.duration._

  override def getState: Future[GetStatePAResponse] =
    ask(actor, GetStatePACommand)(Timeout.durationToTimeout(1 seconds))
      .mapTo[GetStatePAResponse]

  override def updateEntity(rfvd: RefValDyn): Future[UpdateEntityPAResponse] =
    ask(actor, UpdateEntityPACommand(rfvd))(Timeout.durationToTimeout(1 seconds))
      .mapTo[UpdateEntityPAResponse]

  override def createEntity(e:Data):Future[CreateEntityPAResponse]=
    ask(actor, CreateEntityPACommand(e))(Timeout.durationToTimeout(1 seconds)).mapTo[CreateEntityPAResponse]

  override def setState(s:TestDataLabel): Future[SetStatePAResponse] =
    ask(actor, SetStatePACommand(s))(Timeout.durationToTimeout(1 seconds))
    .mapTo[SetStatePAResponse]

}
