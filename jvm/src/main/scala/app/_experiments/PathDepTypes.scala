package app._experiments

//import app._experiments.BRoute.ParInt
import app._experiments._

import scalaz.\&/.This


/**
  * Created by joco on 01/11/2017.
  */
case class A(){
  case class B()
  val b=B()
}

object O{
  val a1 = A()
  val a2         =A()
//  val b1: _root_.app._experiments.O.a1.B = a1.b

}


//object BRoute extends RouteType[B]{
//
//  type Par= ParInt
//
//  case class ParInt(a:Int) extends ReqParameter[B]
//}



//                    <= Par[E] <= User
//                    <= ParDyn <= Get/Refresh/Display
// | SERVER <=> CLIENT
//                    => Cache => Screen
//                    => TypeSafe Code => Screen

//server:
// make Directive/Route
// | def f(RoutDescription[E]) => Route - for akka http

//wire server side:
// => toJSON[E](rpl:ResPayload[E])->String
// => fromJSON[E](String) -> (rpl:ReqParameters[E])
// decoder/encoder circe ...


//wire client side:
// => toJSON[E](rpl:ReqParameters[E])->String
// => fromJSON[E](String) -> (rpl:ResPayload[E])
// decoder/encoder circe ...

//client:
// <=> make typesafe ajax call
// def g(RoutDescription[E]) => h(ref:Ref[E]) - > Future [RouteDescription#Res[E]]

// <- translate untyped call from react to type safe ajax call
// this needs a pattern match that translates the untyped call to a typed call
// is that pattern match (CRUD x Entity) long ? yes it is ...





//minden route-hoz (CRUD x Entity) lesz egy uj case class/object ? aminek neve lesz ?

//mi van akkor ha egyenloek ...




//case class ARPar(a:String, b:Int) extends Parameter[RouteName]



