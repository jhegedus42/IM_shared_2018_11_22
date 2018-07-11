package app.shared.data.model

import app.shared.data.model.Entity.Data

import scala.reflect.ClassTag

object Entity {
//  sealed trait Data

  trait Value

//  trait EntityParams

  trait Entity extends Data

  trait Data
  {
    def constraints:List[Constraint]= List()

//    def getType[T2:Typeable]() =EntityType.make[T2](implicitly[Typeable[T2]])
  }
  abstract class Constraint(){
//    def isSatisfied(state:State) : Boolean
  }

//  override def toString: String = this.asJS
}


case class DataType(type_as_string:String){

  def isTypeCorrect[E<:Data:ClassTag]:Boolean= DataType.getTypeAsString[E] == type_as_string
}


object DataType{
//  def make[T](t:Typeable[T])=EntityType(t.describe)
    def getTypeAsString[T<:Data](implicit t:ClassTag[T])= t.runtimeClass.getSimpleName
    def make[T<:Data](implicit t:ClassTag[T])= DataType(getTypeAsString[T])
    def fromEntity(e:Data)= DataType(e.getClass.getSimpleName)
}