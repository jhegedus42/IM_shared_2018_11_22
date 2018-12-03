package app.client

import app.client.ui_v2.app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator_depth1Components.lineDetail.D0_SimpleComp
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


//    val compConstructor : D0_RootCompConstructor = D0_MaterialUIWrapperComp.rootCompConstructor
    val compConstructor  = D0_SimpleComp.simpleCompConstr("hello42")
    // TODO 0 ^^^  replace this with a simple static react page that displays something very
    // simple, like a text ... a hello world ... and then show that in the browser





//    ReactDOM.render( compConstructor(), dom.document.getElementById( "joco" ) )
    ReactDOM.render( compConstructor, dom.document.getElementById( "joco" ) )
  }
}



