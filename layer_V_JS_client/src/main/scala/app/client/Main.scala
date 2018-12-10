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
    // 53acb313_83dd163e


    import slogging._
    LoggerConfig.factory = PrintLoggerFactory()
    LoggerConfig.level   = LogLevel.TRACE

//    val compConstructor = ???
    // TODO - need an example with a router + navigator

    // ReactDOM.render( compConstructor, dom.document.getElementById( "rootComp" ) )


    AppCSS.load
    val e: Element = document.getElementById("rootComp")
    println("element="+e)
    AppRouter.router().renderIntoDOM(e)

  }
}
