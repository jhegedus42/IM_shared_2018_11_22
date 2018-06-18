package app.shared.data.views.v5_type_dep_fun.shared

object Shared {

  abstract class View {
    type Par <: Parameter
    type Res <: Result
  }

  trait Result
  trait Parameter

//  type Par[X <: View] = X#Par
//  type Res[X <: View] = X#Res


  object View1 {

    case class View1_Par(s: String ) extends Parameter

    case class View1_Res(i: Int ) extends Result

    class View1 extends View {
      type Par    = View1_Par
      type Res    = View1_Res
    }

  }


}
