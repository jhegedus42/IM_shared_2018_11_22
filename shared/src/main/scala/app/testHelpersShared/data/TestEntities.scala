package app.testHelpersShared.data

import app.shared.model.UserLineList.LineListElement
import app.shared.model.{EntityType, LineText, User, UserLineList}
import app.shared.model.ref.{Ref, RefDyn, RefVal, RefValDyn, Version}

/**
  * Created by joco on 09/10/2017.
  */
object TestEntities {
  val theUUIDofTheLine = "4ce6fca0-0fd5-4197-a946-90f5e7e00d9d"
  val dummyUUID2: String = "4ce6fca0-0fd5-4197-a946-90f5e7e00d9e"
  val line                     = LineText(title= Some("pina"))
  //    private[this] val line                     = Line()
  val refToLine: Ref[LineText] = Ref.makeWithUUID(theUUIDofTheLine)
  val refValOfLineV0           = RefVal(refToLine, line, Version())
  val refValOfLineV1           = RefVal(refToLine, line, Version().inc())

}

object TestEntitiesForStateThree
{
  lazy val userEntity         = User(name = "joco",password="macska")
  lazy val user1uuid            = "10000000-0000-0000-0000-000000000001"
  lazy val rvdUser: RefValDyn = RefValDyn(RefDyn(user1uuid,EntityType.fromEntity(userEntity)), userEntity, Version())
  lazy val userRef: Ref[User] = rvdUser.r.toRef[User]().toEither.right.get

  lazy val line1                     = LineText(title = Some("l1"))
  lazy val line2                     = LineText(title = Some("l2"))
  lazy val line3                     = LineText(title = Some("l3"))
  lazy val entityTypeL: EntityType   = EntityType.make[LineText]

//  lazy val line1uuid          = "5d19cb20-8cb9-4ed0-a093-24778276c93f"
  lazy val line1uuid            = "00000000-0000-0000-0000-000000000001"
  lazy val line2uuid            = "00000000-0000-0000-0000-000000000002"
  lazy val line3uuid            = "00000000-0000-0000-0000-000000000003"

  lazy val refLine1: Ref[LineText] = Ref[LineText](entityType = entityTypeL, uuid = line1uuid)
  lazy val refLine2                = Ref[LineText](entityType = entityTypeL, uuid = line2uuid)
  lazy val refLine3                = Ref[LineText](entityType = entityTypeL, uuid = line3uuid)

  lazy val lle1 = LineListElement(line = refLine1)
  lazy val lle2 = LineListElement(line = refLine2)
  lazy val lle3 = LineListElement(line = refLine3)

  lazy val list = UserLineList(user = userRef, lines = List(lle1, lle2, lle3))

  lazy val listRefUuid                = "20000000-0000-0000-0000-000000000001"
  lazy val listRef: Ref[UserLineList] = Ref[UserLineList](uuid= listRefUuid, entityType = EntityType.make[UserLineList])

  lazy val listRV = RefVal(listRef,list,Version())
}