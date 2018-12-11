package demo.app.components

import scala.concurrent.{ExecutionContextExecutor, Future}
import japgolly.scalajs.react.ScalaComponent
import japgolly.scalajs.react.vdom.html_<^._
import scalacss.ScalaCssReact._

object Main {
  implicit def executionContext: ExecutionContextExecutor =
    scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
  val component = ScalaComponent.builder[Unit]("Main")
    .renderStatic(<.div(
      demo.app.Styles.app,
      Header(),
      Content()))
    .build
  def apply() = component()
}
