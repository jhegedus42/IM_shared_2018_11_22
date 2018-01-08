package app.server.persistence.persActor

import app.server.State
import app.shared.SomeError_Trait
import app.shared.data.model.Entity.Data
import app.shared.data.ref.RefValDyn
import app.testHelpersShared.data.TestDataLabels.TestDataLabel

import scalaz.\/

/**
  * Created by joco on 09/10/2017.
  */
object Commands {

  //  protocol
  case class UpdateEntityPACommand(entity: RefValDyn)
  case class UpdateEntityPAResponse(payload: \/[SomeError_Trait, RefValDyn])

  case class CreateEntityPACommand[E <: Data](e:Data)
  case class CreateEntityPAResponse(payload: \/[SomeError_Trait, RefValDyn])

  case object GetStatePACommand
  case class GetStatePAResponse(state: State)

  case class SetStatePACommand(tdl:TestDataLabel)
  case class SetStatePAResponse(success: Boolean)
}
