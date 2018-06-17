package app.shared.data.views.m5_type_dep_fun.shared

object Shared {

  abstract
  class WrappedF {
    type F <: WrappedF
    type Unwrap
  }

  type F[X <: WrappedF] = X#F

  class IntF extends WrappedF {
    type Unwrap = Int;
    type F = StringF
  }

  class BooleanF extends WrappedF {
    type Unwrap = Boolean;
    type F = IntF
  }

  class StringF extends WrappedF {
    type Unwrap = String;
    type F = Nothing
  }

  implicitly[String =:= F[IntF]#Unwrap]
  implicitly[Int =:= F[BooleanF]#Unwrap]
  implicitly[String =:= F[F[BooleanF]]#Unwrap]
}