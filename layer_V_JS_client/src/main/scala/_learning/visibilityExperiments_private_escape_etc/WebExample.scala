package _learning.visibilityExperiments_private_escape_etc

package Example1 {

  package thePackage {

    // PRIVATE test class with PUBLIC field
    private[thePackage] class VisibilityTest(value: String, val valueVisible: Int )

    // WRAPPER
    class Wrapper {
      val visibilityTest = new VisibilityTest( "Value set by Wrapper object.", 43 )
    }

  }

  object Tester extends App {
    val wrapper = new thePackage.Wrapper()

//    val wrapped: thePackage.VisibilityTest = wrapper.visibilityTest; // compile ERROR
    val wrapped = wrapper.visibilityTest; // COMPILES FINE -> ????????

//    println(wrapped.value) // ERROR -> as it should be, since value's getter is private
    println( wrapped.valueVisible ) // no ERROR -> ?????????

  }

}
