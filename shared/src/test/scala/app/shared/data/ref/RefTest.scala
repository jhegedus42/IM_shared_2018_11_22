package app.shared.data.ref

import app.shared.data.model.Entity.Data
import org.scalatest.FunSuite

import scala.reflect.ClassTag

/**
  * Created by joco on 09/09/2017.
  */
class RefTest extends FunSuite {

}
object RefValTestUtil {
  def makeWithNewUUID[T <: Data](v: T)
                                (implicit t: ClassTag[T]) : RefVal[T] = new RefVal(Ref.make[T](), v,Version())
}
