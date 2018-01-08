package app.server.stateAccess.generalQueries

import app.shared.SomeError_Trait
import app.shared.data.model.Entity.Data
import app.shared.data.ref.{Ref, RefVal}

import scala.concurrent.Future
import scala.reflect.ClassTag
import scalaz.\/

trait InterfaceToStateAccessor {

  def getEntity[E <: Data: ClassTag](r: Ref[E]): Future[\/[SomeError_Trait, RefVal[E]]]


  def doesEntityExist[E<:Data:ClassTag](e:E) : Future[Boolean]


  def updateEntity[E <: Data: ClassTag](rv: RefVal[E]): Future[\/[SomeError_Trait, RefVal[E]]]



  def createEntity[E <: Data: ClassTag](e: E): Future[\/[SomeError_Trait, RefVal[E]]]
  // itt kell validalni az entity-t (minden parameter meg van adva)
  // kliens kuldi el h mit akar letrehozni, mi meg itt validaljuk...
  // hogy nem e fassag-e


  def shutDownService():Unit

  def getAllEntitiesOfGivenType[E <: Data: ClassTag]: Future[\/[SomeError_Trait, List[RefVal[E]]]]
  //852378b9bfea4650ae3bb01305731725
}

