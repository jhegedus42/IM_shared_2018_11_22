package app.server

import app.shared.model.entities.Entity.Entity
import app.shared.model.entities.EntityType
import app.shared.{EntityDoesNotExistError, EntityIsNotUpdateableError, InvalidVersionError, SomeError_Trait, StateOpsError, TypeError}
import app.shared.model.ref.{Ref, RefDyn, RefVal, RefValDyn}

import scala.reflect.ClassTag
import scalaz.{-\/, Disjunction, \/, \/-}
sealed trait StateType //phantom type
trait Prod extends StateType
trait TestState extends StateType

case class State(val stateMap: Map[RefDyn, RefValDyn] = Map.empty ) {

  case class StateUpdateError(s: String )

  //versions are expected to match

  def insertEntity(refValDyn: RefValDyn ): \/[SomeError_Trait, State] = {
    \/-( this.copy( stateMap = this.stateMap + (refValDyn.r -> refValDyn) ) )
    //todolater thrown an exception if refValDyn.r already exists
  }

  def updateEntity(refValDyn: RefValDyn ): \/[SomeError_Trait, ( State, RefValDyn )] = {
    //check that updateable entity exists
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

//  def updateEntity( refVal: RefValDyn): \/[SomeError_Trait, (State, RefValDyn)] = {
//
////    val en0: \/[SomeError_Trait, RefValDyn = getEntityDyn(refVal.r)
////    if(en0.isRight) {
//    //8bd09adf49324d5a8a616c8146fd6848
//
//
//    val newState: Disjunction[SomeError_Trait, State] =
//      updateEntityInternal(refVal)
//
//    val happyPath: Disjunction[SomeError_Trait, (State, RefValDyn)] =
//      for {
//        ns <- newState
//        refValDyn <- ns.getEntityDyn(refVal.r)
//      } yield ((ns, refValDyn))
//    happyPath
//  }

  def doesEntityExist(e: Entity ): Boolean =
    stateMap.values.map( rvd => rvd.e ).toSet.contains( e )

  def getEntitiesOfGivenType[E <: Entity: ClassTag](): \/[SomeError_Trait, List[RefVal[E]]] = {
    val et: EntityType = EntityType.make[E]
    val r: List[Disjunction[TypeError, RefVal[E]]] =
      stateMap.values.filter( rvd => rvd.r.et == et ).map( _.toRefVal[E] ).toList

    if (r.forall( dj => dj.isRight ))(\/-( r.map( _.toEither.right.get ) ) )
    else -\/( StateOpsError( "getEntities type error - this should not happen" ) )
  }

  private[server] def getEntity[E <: Entity: ClassTag](r: Ref[E] ): \/[SomeError_Trait, RefVal[E]] = {
    //ffd417f7defb4ee3b542a2d7d68e6b42

    if (!r.isTypeCorrect) return -\/( TypeError( "State.getEntity - 1" ) )
    else {
      val rd: RefDyn = r
      getEntityDyn( rd ).flatMap( _.toRefVal[E] ) // checks that the dyn type from the map matches with the expected type E
    }
  }

  import scalaz._
  import Scalaz._
  private def getEntityDyn(rd: RefDyn ): \/[SomeError_Trait, RefValDyn] = {
    //todolater we need to check if the type of the entity returned by this method
    // agrees with `the type of rd`
    val r: Option[RefValDyn] = this.stateMap.get( rd )
    val r2 =
      r.toRightDisjunction( EntityDoesNotExistError( s"StateOps.getEntity " + rd ) )
    r2 match {
      case -\/( a ) => -\/( a )
      case \/-( b ) => {
        if (b.r.et == rd.et) \/-( b )
        else -\/( (TypeError( "State.getEntity - 2" ) ) )
        //if the entity in the map does not have the sme type as the one in RefDyn
      }
    }

  }

}
