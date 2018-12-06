package app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator

/**
  *
  * Information used (by whom?) for constructing the child of the navigator.
  *
  * ebe6daa0_83dd163e
  */

sealed trait URL_STr

case class LineDetail_URL(idOfLine: java.util.UUID )
  extends URL_STr

case class LineList_Page() extends URL_STr

case class LineListsOfUser_URL(id_ofUser: java.util.UUID )
  extends URL_STr
