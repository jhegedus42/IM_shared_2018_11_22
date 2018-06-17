package app.shared.data.views.m5_type_dep_fun.shared

object Shared {

  abstract
  class Lifted {
    type Par <: Lifted
    type Res <: Lifted
    type Unwrap
  }

  type Par[X <: Lifted] = X#Par
  type Res[X <: Lifted] = X#Res

  trait View


  class View1 extends Lifted with View {
    type Unwrap = Nothing;
    type Par = StringF
    type Res = IntF
  }

  class IntF extends Lifted {
    type Unwrap = Int;
//    type Par = StringF
  }

  class BooleanF extends Lifted {
    type Unwrap = Boolean;
//    type Par = IntF
  }

  class StringF extends Lifted {
    type Unwrap = String;
//    type Par = Nothing
//    type Res = Nothing
  }


}

