package app.shared.data.model

import app.shared.data.model.Entity.Data

import scala.reflect.ClassTag

object Entity {
//  sealed trait Data

  /**
    * Ez egy olyan adat amit nem szabad referenciába burkolni, de mégis tárolhatunk
    * az appunkban.
    *  - De akkor ennek mi értelme van ?
    *    - Miért van erre egyáltalán szükség ?
    *     - pl a Rect esetében v. az Img esetében ?
    *       - Ez csak valami fantom típus ?
    *         - Ami mit hivatott jelezni ?
    *           - Elvileg azt, hogy ezt nem updatelhetjük, anélkül, hogy
    *             magát a birtokló case class-t nem módosítanánk.
    *             - Azaz Rect és Img nem létezhet csak úgy önmagában ?
    *             - Azaz nem lehet Ref-fel hivatkozni rá ?
    *             - Azaz nincsen identitása ?
    *               - Az mit jelent, hogy nincsen identitása ?
    *             - Ami inkább fontosabb, az az, hogy meg kell tiltani azt, hogy két különböző
    *               Entity that have identities, that are mutable, are
    *             - Tehát akkor az ApplicationState-ben át kell írni a típusokat.
    *
    */

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