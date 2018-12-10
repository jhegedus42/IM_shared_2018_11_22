package app.client

import app.client.ui.css.AppCSS
import app.client.ui.routes.AppRouter
import org.scalajs.dom.document
import org.scalajs.dom.raw.Element

import scala.scalajs.js.JSApp

object ReactApp extends JSApp {
  def main(): Unit = {
    println("elkezd futni a demo App")

    import slogging._ // ez valamiert itt volt az elozo appbol, de miert ?
    LoggerConfig.factory = PrintLoggerFactory()
    LoggerConfig.level   = LogLevel.TRACE

    AppCSS.load
    val e: Element = document.getElementById("rootComp")
    println("element="+e)
    AppRouter.router().renderIntoDOM(e)
  }
}






