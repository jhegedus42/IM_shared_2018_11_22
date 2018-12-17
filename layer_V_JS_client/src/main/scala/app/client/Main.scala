package app.client

import app.client.ui.css.AppCSS
import app.client.ui.routes.AppRouter
import org.scalajs.dom.document
import org.scalajs.dom.raw.Element

import scala.concurrent.ExecutionContextExecutor
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport

// this needs to be supressed
// we can have one such export
// otherwise ScalaJS fastOptJS fails
@JSExport( "Main" )
object Main extends js.JSApp {
  implicit def executionContext: ExecutionContextExecutor =
    scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

  @JSExport
  def main(): Unit = {


    import slogging._
    LoggerConfig.factory = PrintLoggerFactory()
    LoggerConfig.level   = LogLevel.TRACE
      AppCSS.load
    val e: Element = document.getElementById("rootComp")
    AppRouter.router().renderIntoDOM(e)

  }
}
