package app.client.ui.pages.main

import app.client.ui.pages.main.root_children.materialUI_children.Pages.Page
import app.client.ui.pages.main.root_children.materialUI_children.RouterComp
import chandu0101.scalajs.react.components.materialui.{Mui, MuiMuiThemeProvider}
import japgolly.scalajs.react.{ReactComponentB, ReactComponentC, TopNode}
import japgolly.scalajs.react.extra.router.Router

/**
  *
  * [[RootComponent.rootCompConstructor]] is used by [[japgolly.scalajs.react.ReactDOM.render()]]
  * in [[app.client.Main]] to create the root react component.
  *
  * The React Comp Hierarchy is the following :
  * [[RootComponent]] -> [[MuiMuiThemeProvider]] -> [[RouterComp]] ->
  *
  *
  */

object RootComponent {

  // Set up a theme based on default light theme, but replacing cyan with
  // blueGrey
  private val baseTheme = Mui.Styles.LightRawTheme
  private val theme = Mui.Styles.getMuiTheme(
    baseTheme.copy(
      palette = baseTheme.palette
        .copy( primary1Color = Mui.Styles.colors.blueGrey500 )
        .copy( primary2Color = Mui.Styles.colors.blueGrey700 )
        .copy( accent1Color = Mui.Styles.colors.deepOrangeA200 )
    )
  )

  // Our top-level component, display pages based on URL, with app bar and navigation
  //val router = DemoRoutes.router

  private val router: Router[Page] = RouterComp.constructor()

  // Need to wrap our top-level router component in a theme for Material-UI to work
  // 548215156450c85ecbb738120e5b4139548215156450c85ecbb738120e5b4139

  val rootCompConstructor: ReactComponentC.ConstProps[Unit, Unit, Unit, TopNode] =
    ReactComponentB[Unit]( "themedView" )
      .render(
        _ =>
          MuiMuiThemeProvider( muiTheme = theme )(
            router()
        )
      )
      .build

}
