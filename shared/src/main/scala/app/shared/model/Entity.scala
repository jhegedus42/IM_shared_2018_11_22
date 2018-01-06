package app.shared.model

import app.shared.model.Entity.Entity
import app.shared.model.ref.Ref
import app.shared.model.utils.PrettyPrint

import scala.reflect.ClassTag

object Entity {
  sealed trait Data

  trait Value extends Data

  trait EntityParams

  trait Entity extends Data
  {
    def constraints:List[Constraint]= List()

//    def getType[T2:Typeable]() =EntityType.make[T2](implicitly[Typeable[T2]])
  }
  abstract class Constraint(){
//    def isSatisfied(state:State) : Boolean
  }

//  override def toString: String = this.asJS
}


case class EntityType(type_as_string:String){

  def isTypeCorrect[E<:Entity:ClassTag]:Boolean= EntityType.getTypeAsString[E] == type_as_string
}


object EntityType{
//  def make[T](t:Typeable[T])=EntityType(t.describe)
    def getTypeAsString[T<:Entity](implicit t:ClassTag[T])=t.runtimeClass.getSimpleName
    def make[T<:Entity](implicit t:ClassTag[T])= EntityType(getTypeAsString[T])
    def fromEntity(e:Entity)=EntityType(e.getClass.getSimpleName)
}