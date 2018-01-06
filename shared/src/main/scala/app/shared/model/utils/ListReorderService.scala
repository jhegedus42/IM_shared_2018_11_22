package app.shared.model.utils

import scala.collection.mutable.ListBuffer

/**
  * Created by joco on 28/04/2017.
  */
case class ListReorderService(oldIndex: Int, newIndex: Int) {
  def getReorderedList[A](l: List[A]): List[A] = {
    val lb = ListBuffer(l: _*)
    val e = lb.remove(oldIndex)
    lb.insert(newIndex, e)
    lb.toList //copy of the list
  }
}
