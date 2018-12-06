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

    val compConstructor = ???
    // TODO - need an example with a router + navigator

    // ReactDOM.render( compConstructor, dom.document.getElementById( "rootComp" ) )



  }
}
