package app.shared.data.views.v4_path_dep_type_based_view_architecture.server.logic

import app.shared.data.views.v4_path_dep_type_based_view_architecture.shared.ArchitectureModel.{View, View1, View1_ViewParams_Instance, ViewParams}

trait ServerSideFunction[V<:View] {
//  def g[Req,Resp](implicit v:ViewParams[V],f:ViewParams.Aux[V,Req,Resp]):Resp = {

//  }Resp
//  def f(i:Req):Resp
}

object ServerSideFunction {

//  implicit object View1ServerSideImplicit
//    extends ServerSideFunction[View1]
  {
//    override def f(i: Req): Resp = {
//      ???
//    }
  }


}

object Test{


  abstract class WrappedF[T] {
    type F
    type Unwrap = T
  }

  type F[X <: WrappedF[_]] = X#F

  class IntF      extends WrappedF[Int]     { type F = StringF }
  class BooleanF  extends WrappedF[Boolean] { type F = IntF }
  class StringF   extends WrappedF[String]  { type F = Nothing }

  implicitly[String =:= F[IntF]#Unwrap]
  implicitly[Int =:=    F[BooleanF]#Unwrap]
  implicitly[String =:= F[F[BooleanF]]#Unwrap]

  trait Test[V <: WrappedF[V]]{
//    def f(a: F[V]#Unwrap): F[V]#Unwrap
  }

//  implicit object TestImpl extends Test[IntF]{
//    override def f(a: F[IntF]#Unwrap): F[IntF]#Unwrap = {
//      val z: F[IntF]#Unwrap = "fd"+a
//      z
//    }
//  }

//  def g[V<:WrappedF[_]](implicit t:Test[V]) : F[V] = {
//    val s: Test[V] = t
//    s.f()
//  }






}

object Test2{

  // sealed for closed family
  class F[I] { type O }


  object F {

    type Rel[I, O0] = F[I] { type O = O0 }
    /* private */ def mkF[I, O0]: Rel[I, O0] = new F[I] { override type O = O0 }

    implicit val fInt: Rel[Int, String] = mkF
    implicit val fBoolean: Rel[Boolean, Int] = mkF

    def apply[I](implicit f: F[I]): f.type = f
    // f.type survives the refinement (see what breaks on removal)

  }

  locally { val temp = F[Int]; implicitly[temp.O =:= String] }
  locally { val temp = F[Boolean]; implicitly[temp.O =:= Int] }
  locally { val temp0 = F[Boolean]; val temp1 = F[temp0.O]; implicitly[temp1.O =:= String] }

}


object TestPathDepType{

  trait View[V]
  {
    type Req
    type Resp
  }
  trait View1
  trait View2

  implicit object View1 extends View[View1]{
    case class View1Req(s:String)
    case class View1Res(s:String)

    override type Req  =View1Req
    override type Resp =View1Res
  }





  trait Server[V] extends View[V]{
    def f(a: Req )(implicit v:View[V]) : Resp
  }

//  object ServerImpl extends Server[View1]{
//    override def f(a: Req )(implicit v:View[View1]): Resp =
//    {
//
//    }
//  }


}



