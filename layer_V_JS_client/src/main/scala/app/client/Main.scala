package app.client

import app.client.ui_v2.D0_MaterialUIWrapperComp
import app.client.ui_v2.D0_MaterialUIWrapperComp.RootCompConstructor
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

//    MyStyles.addTo Document()

    // 8145b90cc28755c8e070e99187dd92ad
    // 53acb313_83dd163ea

//    val compConstructor : RootCompConstructor = RootComponent.rootCompConstructor
    val compConstructor : RootCompConstructor = D0_MaterialUIWrapperComp.rootCompConstructor

//    ReactDOM.render( compConstructor(), dom.document.getElementById( "joco" ) )
    ReactDOM.render( compConstructor(), dom.document.getElementById( "joco" ) )
//    ReactDOM.render(OuterComp.comp("Start State"),
//                    scalajs.dom.document.getElementById("joco"))
  }
}

//object CSSTest {
//  import scalacss.DevDefaults._
//
//  object MyStyles extends StyleSheet.Inline {
//    import dsl._
//
//    val common = mixin(
//      backgroundColor.green
//    )
//
//    val outer = style(
//      common, // Applying our mixin
//      margin( 12 px, auto ),
//      textAlign.left,
//      cursor.pointer,
//      &.hover(
//        cursor.zoomIn
//      ),
//      media.not.handheld.landscape.maxWidth( 640 px )(
//        width( 400 px )
//      )
//    )
//
//    /** Style requiring an Int when applied. */
//    val indent =
//      styleF.int( 0 to 3 )(
//        i =>
//          styleS(
//            paddingLeft( i * 2.ex )
//        )
//      )
//
//    /** Style hooking into Bootstrap. */
//    val button = style(
//      addClassNames( "btn", "btn-default" )
//    )
//  }
//
//}
