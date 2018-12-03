package app.client

import app.client.ui_v2.D0_MaterialUIWrapperComp
import app.client.ui_v2.D0_MaterialUIWrapperComp.D0_RootCompConstructor
import japgolly.scalajs.react.ReactDOM
import org.scalajs.dom

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


    val compConstructor : D0_RootCompConstructor = D0_MaterialUIWrapperComp.rootCompConstructor
    // TODO 0 ^^^  replace this with a simple static react page that displays something very
    // simple, like a text ... a hello world



//    ReactDOM.render( compConstructor(), dom.document.getElementById( "joco" ) )
    ReactDOM.render( compConstructor(), dom.document.getElementById( "joco" ) )
  }
}

