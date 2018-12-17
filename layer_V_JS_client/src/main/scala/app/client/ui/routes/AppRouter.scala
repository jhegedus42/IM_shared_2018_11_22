package app.client.ui.routes

import app.client.ui.components.{Footer, TopNav}
import app.client.ui.models.Menu
import app.client.ui.pages.HomePage
import japgolly.scalajs.react.extra.router.StaticDsl.Route
import japgolly.scalajs.react.extra.router.{Resolution, RouterConfigDsl, RouterCtl, _}
import japgolly.scalajs.react.vdom.html_<^._
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Js
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.html_<^._


object AppRouter {





  sealed trait AppPage

  case object Home extends AppPage
  case class Items(p: Item) extends AppPage
  case class ItemPage(id: Int) extends AppPage

  val itemPage = ScalaComponent.builder[ItemPage]("Item page")
                 .render({
                           println("render lefut")
                           p => <.div(s"Info for item #${p.props.id}")
                         })
                 .build
  // ^^^ ennek kell adni egy state-et


  val config = RouterConfigDsl[AppPage].buildConfig { dsl =>
    import dsl._

    val dynItemRoute: dsl.Rule = dynamicRouteCT( "#item" / int.caseClass[ItemPage]) ~> dynRender(itemPage(_))

    val itemRoutes: dsl.Rule =
      Item.routes.prefixPath_/("#items").pmap[AppPage](Items) {
        case Items(p) => p
      }

    val homeRoute: dsl.Rule = staticRoute(root, Home) ~> render(HomePage())


    (trimSlashes
      | homeRoute
      | dynItemRoute
      | itemRoutes
       )
      .notFound(redirectToPage(Home)(Redirect.Replace))
      .renderWith(layout)
  }

  val mainMenu = Vector(
    Menu("Home", Home),
    Menu("Items", Items(Item.Info))
  )

  def layout(c: RouterCtl[AppPage], r: Resolution[AppPage]) =
  {
    // pl ide lehetne rakni vmit ami updateli a Cache.currentChild-jat ...
    // odaadja neki a resolution-t

    println(s"page = ${r.page}")
    <.div(
           TopNav(TopNav.Props(mainMenu, r.page, c)),
           r.render(),
           Footer() //
         )
  }

//  val baseUrl = BaseUrl.fromWindowOrigin / "scalajs-react-template/"
  val baseUrl = BaseUrl.fromWindowOrigin_/

  val router  = Router(baseUrl, config)
}
