package app.client.ui.pages.main

import Pages.Page
import chandu0101.scalajs.react.components.materialui.{Mui, MuiMuiThemeProvider}
import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.extra.router.Router

object RootComponent {

  // Set up a theme based on default light theme, but replacing cyan with
  // blueGrey
  val baseTheme = Mui.Styles.LightRawTheme
  val theme = Mui.Styles.getMuiTheme(
    baseTheme.copy(
      palette = baseTheme.palette
        .copy(primary1Color = Mui.Styles.colors.blueGrey500)
        .copy(primary2Color = Mui.Styles.colors.blueGrey700)
        .copy(accent1Color = Mui.Styles.colors.deepOrangeA200))
  )

  // Our top-level component, display pages based on URL, with app bar and navigation
  //val router = DemoRoutes.router

  val router: Router[Page] = RouterComp.constructor()

  // Need to wrap our top-level router component in a theme for Material-UI to work
  // 548215156450c85ecbb738120e5b4139548215156450c85ecbb738120e5b4139
  val rootCompConstructor = ReactComponentB[Unit]("themedView")
    .render(
      p =>
        MuiMuiThemeProvider(muiTheme = theme)(
          router()
      ))
    .build

}
