package app.server.persistence

import akka.actor.ActorRef
import akka.util.Timeout
import app.server.persistence.persActor.Commands.{CreateEntityPACommand, CreateEntityPAResponse, GetStatePACommand, GetStatePAResponse, SetStatePACommand, SetStatePAResponse, UpdateEntityPACommand, UpdateEntityPAResponse}

import app.shared.model.entities.Entity.Entity
import app.shared.model.ref.RefValDyn
import app.testHelpersShared.data.TestDataLabels.TestDataLabel
import app.testHelpersShared.implicits.ForTestingOnly

import scala.concurrent.Future


/**
  * Created by joco on 09/10/2017.
  */
trait PersActorWrapperIF{
  def getState: Future[GetStatePAResponse]

  def updateEntity(rfvd: RefValDyn): Future[UpdateEntityPAResponse]

  def createEntity(e:Entity):Future[CreateEntityPAResponse]

  def setState(s:TestDataLabel): Future[SetStatePAResponse]
}

class PersActorWrapper(private[this] val actor: ActorRef) extends PersActorWrapperIF{
  import akka.pattern.ask
  import akka.util.Timeout

  import scala.concurrent.duration._

  override def getState: Future[GetStatePAResponse] =
    ask(actor, GetStatePACommand)(Timeout.durationToTimeout(1 seconds))
    //todolater test what happens if this times out and see what should i do about it... how should
    // i handle it
      .mapTo[GetStatePAResponse]

  override def updateEntity(rfvd: RefValDyn): Future[UpdateEntityPAResponse] =
    ask(actor, UpdateEntityPACommand(rfvd))(Timeout.durationToTimeout(1 seconds))
    //todolater test what happens if this times out and see what should i do about it... how should
    // i handle it
      .mapTo[UpdateEntityPAResponse]

  override def createEntity(e:Entity):Future[CreateEntityPAResponse]=
    ask(actor, CreateEntityPACommand(e))(Timeout.durationToTimeout(1 seconds)).mapTo[CreateEntityPAResponse]

  override def setState(s:TestDataLabel): Future[SetStatePAResponse] =
    ask(actor, SetStatePACommand(s))(Timeout.durationToTimeout(1 seconds))
    //todolater test what happens if this times out and see what should i do about it... how should
    // i handle it
    .mapTo[SetStatePAResponse] //8cecd7dc2bde483f853b1dd2420930e9

}
