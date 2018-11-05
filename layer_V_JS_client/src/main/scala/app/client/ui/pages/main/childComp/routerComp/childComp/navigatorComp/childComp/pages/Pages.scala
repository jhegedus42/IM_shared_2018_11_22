package app.client.ui.pages.main.childComp.routerComp.childComp.navigatorComp.childComp.pages

object Pages {
  sealed trait Page
  case object LineListPage extends Page
  case object ReorderList extends Page
  case class LineDetailPage(id:        java.util.UUID ) extends Page
  case class UserLineListPage(id_user: java.util.UUID ) extends Page
}
