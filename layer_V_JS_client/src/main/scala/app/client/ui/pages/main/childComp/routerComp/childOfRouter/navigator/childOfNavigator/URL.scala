package app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator

/**
  *
  * Information used (by whom?) for constructing the child of the navigator.
  *
  * ebe6daa0_83dd163e
  */

sealed trait URL

case class LineDetail_Page(idOfLine: java.util.UUID )
  extends URL

case class LineList_Page() extends URL

case class LineListsOfUser(id_ofUser: java.util.UUID )
  extends URL
