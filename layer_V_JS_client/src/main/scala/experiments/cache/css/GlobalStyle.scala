package experiments.cache.css
import scalacss.DevDefaults._
//import scalacss.Defaults._
import scalacss.ScalaCssReact._
//import scalacss.Defaults._
import scalacss.DevDefaults._
import scalacss.internal.mutable.GlobalRegistry
import org.scalajs.dom.document
import org.scalajs.dom.raw.Element

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import scalacss.ScalaCssReact._

//import scalacss.Defaults._
import scalacss.internal.mutable.GlobalRegistry
object GlobalStyleAppWithCache extends StyleSheet.Inline {

  import dsl._
  style(
         unsafeRoot("body")(
                             margin.`0`,
                             padding.`0`,
                             fontSize(14.px),
                             fontFamily := "Roboto, sans-serif",
                             backgroundColor.dimgrey,
                             color.white
                           )
       )
}
object AppWithCacheCSS {

  def load = {
    GlobalRegistry.register(GlobalStyleAppWithCache)
    GlobalRegistry.onRegistration(_.addToDocument())
  }
}