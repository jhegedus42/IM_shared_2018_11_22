package app.client

import app.client.ui.css.AppCSS
import app.client.ui.routes.AppRouter
import org.scalajs.dom.document
import org.scalajs.dom.raw.Element

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport

@JSExport( "Main" )
object Main extends js.JSApp {

  @JSExport
  def main(): Unit = {


    import slogging._
    LoggerConfig.factory = PrintLoggerFactory()
    LoggerConfig.level   = LogLevel.TRACE


    AppCSS.load
    val e: Element = document.getElementById("rootComp")
    println("element="+e)
    AppRouter.router().renderIntoDOM(e)

  }
}
