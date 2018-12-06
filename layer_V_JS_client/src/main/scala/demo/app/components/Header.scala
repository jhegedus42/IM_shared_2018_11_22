package demo.app.components

import japgolly.scalajs.react.ScalaComponent
import japgolly.scalajs.react.vdom.html_<^._
import scalacss.ScalaCssReact._

object Header {
  val logo_roots="www/assets/logo/"
  val component =
    ScalaComponent.builder[Unit]("Header")
      .renderStatic(<.div(
        demo.app.Styles.appHeader,
        <.img(
          demo.app.Styles.appLogo,
          ^.src := s"${logo_roots}scala-js-logo.svg"),
        <.img(
          demo.app.Styles.appLogo,
          ^.src := s"${logo_roots}react-logo.svg"),
        <.h2("Welcome to Scala.js and React")
      ))
      .build
  def apply() = component()
}
