package app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator_depth1Components.lineList

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
