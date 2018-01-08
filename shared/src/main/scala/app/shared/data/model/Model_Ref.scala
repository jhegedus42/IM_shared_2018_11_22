package app.shared.data.model

import app.shared.data.model.Entity.{Entity, Value}
import app.shared.data.model.UserLineList.LineListElement
import app.shared.data.model.UserType.Normal
import app.shared.data.ref.Ref



case class Date(date:java.util.Date=java.util.Calendar.getInstance().getTime())

import enumeratum._


//  @Lenses
sealed trait UserType extends EnumEntry

case object UserType extends Enum[UserType] with CirceEnum[UserType] {

  case object Admin extends UserType
  case object Normal extends UserType
  case object Guest extends UserType

  val values = findValues
}

//b3beb0310c464f69ad37651ab49a1c42

case class User(name: String,
                email: Option[String] = None,
                password: String,
                userType:UserType=Normal)
  extends Entity

case class UserLineList(user     : Ref[User],
                        //the owner of this line-list, this expresses a constraint, only user can refer to a given list
                        name     : Option[String] = None,
                        isPrivate: Boolean = false,
                        lines    : List[LineListElement]=List())
    extends Entity

object UserLineList {
  case class LineListElement(line: Ref[LineText], que: Option[Ref[ImgQue]]= None) extends Entity {
    // que itt megmondja h. melyik ImgQue-hoz tartozik ez a line list element
    // az itt levo ImgQue csak azok kozul az ImgQue-k kozul kerelhet ki
    // akiknek a line field-je megegyezik a ennek az entity-nek a line fieldjevel
  }

}

case class Tag(name:String) extends Entity

case class LineText(title: Option[String]= None, text: Option[String] = None,
                    tags : Set[Ref[Tag]]= Set(), derivedFrom:Option[Ref[LineText]]= None)
    extends Entity //ez maga a szoveg, ez benne lehet tobb listaban is


case class ImgQue(rect1: Option[ImgQue.Rect],
                  rect2: Option[ImgQue.Rect],
                  line: Ref[LineText],
                  // megmondja melyik Line-hoz passzol ez a Que
                  // tobb Que is passzolhat uahhoz a Line-hoz
                  img: ImgQue.Img)
    extends Entity

// ket ImageQue-t az image alapjan hasonlitunk ossze

object ImgQue {

  case class Rect(x: Int, y: Int, w: Int, h: Int) extends Value

  case class Img(hash: String) extends Value
  // egy user-nek ketszer uaz az image nem lehet
  // mert akkor  ket line-hoz kapcsolodna uaz az image

}

case class SharedLines(lineLists:List[LineText]) extends Entity
// this is singleton, for now

case class LineLike(user:Ref[User],sharedLine:Ref[LineText]) extends Entity
//only shared lines can be liked
