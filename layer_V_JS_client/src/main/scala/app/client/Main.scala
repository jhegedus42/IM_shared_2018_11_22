package app.client

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

//       val compConstructor = ??? // TODO - make this concrete

    //  val compConstructor : D0_RootCompConstructor = D0_MaterialUIWrapperComp.rootCompConstructor
//    val compConstructor  = D0_SimpleComp.simpleCompConstr("hello42")
    // TODO 0 ^^^  replace this with a simple static react page that displays something very
    // simple, like a text ... a hello world ... and then show that in the browser

//    ReactDOM.render( compConstructor(), dom.document.getElementById( "joco" ) )
//    ReactDOM.render( compConstructor, dom.document.getElementById( "rootComp" ) )
  }
}
