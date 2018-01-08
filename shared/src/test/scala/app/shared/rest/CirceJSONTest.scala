package app.shared.rest

import app.shared.data.model.EntityType
import app.shared.data.ref.{RefVal, RefValTestUtil}
//import app.shared.rest.GetEntityRoute.HttpGetEntityReqResult
//import app.shared.rest.GetEntityRoute.Shared.HttpGetEntityReqResult
import org.scalatest.{FunSuite, Matchers}

/**
  * Created by joco on 10/05/2017.
  */
//class CirceJSONTest extends WordSpec with Matchers{
class CirceJSONTest extends FunSuite with Matchers {
  import app.shared.data.model.LineText
  import app.shared.data.ref.Ref
  import io.circe._
  import io.circe.generic.auto._
  import io.circe.parser._
  import io.circe.syntax._

  import scala.util.Right

  object RefValFix{

    val l: LineText          = LineText()
    val rv: RefVal[LineText] = RefValTestUtil.makeWithNewUUID(l)
  }
  test("Decode Ref WithOut TypeCheck") {
    val r: Ref[LineText] = Ref.make[LineText]()
    val r2: Ref[LineText] = Ref.make[LineText]()
    val rs: String = r.asJson.spaces4

    val decoded: Either[Error, Ref[LineText]] = decode[Ref[LineText]](rs)
    assert(1 == 1)
    decoded shouldEqual Right(r)
    decoded should not equal Right(r2)
  }

  test("Decode RefVal WithOut TypeCheck") {
    val l: LineText = LineText()
    val rv: RefVal[LineText] = RefValTestUtil.makeWithNewUUID(l)
    val rv2: RefVal[LineText] = RefValTestUtil.makeWithNewUUID(l)
    val rs: String = rv.asJson.spaces4
    println(rv)

    val decoded: Either[Error, RefVal[LineText]] = decode[RefVal[LineText]](rs)
    assert(1 == 1)
    decoded shouldEqual Right(rv)
    decoded should not equal Right(rv2)
  }

  test("Decode incorrect RefVal With TypeCheck should be left") {
    val l: LineText = LineText()
    import monocle.macros.syntax.lens._

    val ltemp: RefVal[LineText] = RefValTestUtil.makeWithNewUUID(l)
    val rv2_wrong_type: RefVal[LineText] = ltemp.lens(_.r.entityType).set(EntityType("bla"))
    val rs: String = rv2_wrong_type.asJson.spaces4

    println(rv2_wrong_type)

    import CirceJSON.typedRefValDecode
    val decoded: Either[Error, RefVal[LineText]] = typedRefValDecode[LineText](rs)
    assert(decoded.isLeft)
    println(decoded)
  }

  test("Decode correct RefVal With TypeCheck should be right") {
    val l: LineText = LineText()

    val rv2: RefVal[LineText] = RefValTestUtil.makeWithNewUUID(l)
    val rs: String = rv2.asJson.spaces4

    println(rv2)

    import CirceJSON.typedRefValDecode
    val decoded: Either[Error, RefVal[LineText]] = typedRefValDecode[LineText](rs)
    decoded === Right(rv2)
    println(decoded)
  }
//
//  test("Decode incorrect Ref With TypeCheck should be left") {
//    val l: LineText = LineText()
//    import monocle.macros.syntax.lens._
//
//    val rv2_wrong_type: Ref[LineText] =
//      Ref.make[LineText]().lens(_.entityType).set(EntityType("bla"))
//    val rs: String = rv2_wrong_type.asJson.spaces4
//
//    println(rv2_wrong_type)
//
//    val decoded: Either[Error, Ref[LineText]] = CirceJSON.typedRefDecode[LineText](rs)
//    assert(decoded.isLeft)
//    println(decoded)
//  }
//
//  test("Decode correct Ref With TypeCheck should be Right") {
//    val rv2: Ref[LineText] = Ref.make[LineText]()
//
//    val rs: String = rv2.asJson.spaces4
//
//    val decoded: Either[Error, Ref[LineText]] = CirceJSON.typedRefDecode[LineText](rs)
//    decoded.right === rv2
//  }
//
//  test("Encode and Decode HttpGetReqResult[Line]") {
//
//
//    val e: HttpGetEntityReqResult[LineText] = HttpGetEntityReqResult(\/-(RefValFix.rv))
//    val s=e.asJson.spaces4
//    val e1: Either[Error, HttpGetEntityReqResult[LineText]] = decode[HttpGetEntityReqResult[LineText]](s)
//    assert(e1.right.get == e)
//    e1.right.get shouldEqual e
//
//    //todolater try this https://github.com/typelevel/scalaz-scalatest for \/ matching
//
//
//  }



}
