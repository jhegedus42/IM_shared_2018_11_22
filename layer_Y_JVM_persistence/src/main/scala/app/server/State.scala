package app.server

import app.shared.data.model.Entity.Data
import app.shared.data.model.DataType
import app.shared.{EntityDoesNotExistError, EntityIsNotUpdateableError, InvalidVersionError, SomeError_Trait, StateOpsError, TypeError}
import app.shared.data.ref.{Ref, RefDyn, RefVal, RefValDyn}

import scala.reflect.ClassTag
import scalaz.{-\/, Disjunction, \/, \/-}
sealed trait StateType //phantom type
trait Prod extends StateType
trait TestState extends StateType

case class State(stateMap: Map[RefDyn, RefValDyn] = Map.empty ) {

  case class StateUpdateError(s: String )

  def insertEntity(refValDyn: RefValDyn ): \/[SomeError_Trait, State] = {
    \/-( this.copy( stateMap = this.stateMap + (refValDyn.r -> refValDyn) ) )
  }

  def updateEntity(refValDyn: RefValDyn ): \/[SomeError_Trait, ( State, RefValDyn )] = {
    val rr: RefDyn = refValDyn.r

    if (!stateMap.contains( rr ))
      return -\/(
        EntityIsNotUpdateableError(
          "entity does not exist",
          Some( EntityDoesNotExistError( "while updating State" ) )
        )
      )

    if (refValDyn.version == stateMap( refValDyn.r ).version) {
      val newVal = refValDyn.copy( version = refValDyn.version.inc() )
      return \/-( this.copy( stateMap = this.stateMap + (refValDyn.r -> newVal) ), newVal )
    } else {
      val r = -\/(
        InvalidVersionError( "while updating the state", stateMap( refValDyn.r ).version, refValDyn.version )
      )
      return r
    }

  }

  def doesEntityExist(e: Data ): Boolean =
    stateMap.values.map( rvd => rvd.e ).toSet.contains( e )

  def getEntitiesOfGivenType[E <: Data: ClassTag](): \/[SomeError_Trait, List[RefVal[E]]] = {
    val et: DataType = DataType.make[E]
    val r: List[Disjunction[TypeError, RefVal[E]]] =
      stateMap.values.filter( rvd => rvd.r.et == et ).map( _.toRefVal[E] ).toList

    if (r.forall( dj => dj.isRight ))(\/-( r.map( _.toEither.right.get ) ) )
    else -\/( StateOpsError( "getEntities type error - this should not happen" ) )
  }

  private[server] def getEntity[E <: Data: ClassTag](r: Ref[E] ): \/[SomeError_Trait, RefVal[E]] = {

    if (!r.isTypeCorrect) return -\/( TypeError( "State.getEntity - 1" ) )
    else {
      val rd: RefDyn = r
      getEntityDyn( rd ).flatMap( _.toRefVal[E] )
      // checks that the dyn type from the map matches with the expected type E
    }
  }

  import scalaz._
  import Scalaz._

  private def getEntityDyn(rd: RefDyn ): \/[SomeError_Trait, RefValDyn] = {
    val r: Option[RefValDyn] = this.stateMap.get( rd )
    val r2 =
      r.toRightDisjunction( EntityDoesNotExistError( s"StateOps.getEntity " + rd ) )
    r2 match {
      case -\/( a ) => -\/( a )
      case \/-( b ) => {
        if (b.r.et == rd.et) \/-( b )
        else -\/( (TypeError( "State.getEntity - 2" ) ) )
      }
    }

  }

}
