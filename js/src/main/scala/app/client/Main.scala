package app.client

import app.client.ui.pages.main.RootComponent._
import japgolly.scalajs.react.vdom.ReactTagOf
import japgolly.scalajs.react.{ReactComponentU, ReactDOM, TopNode}
import org.scalajs
import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport

@JSExport( "Main" )
object Main extends js.JSApp {

  @JSExport
  def main(): Unit = {

    import slogging._
    LoggerConfig.factory = PrintLoggerFactory()
    LoggerConfig.level   = LogLevel.TRACE

//    MyStyles.addToDocument()

    // 8145b90cc28755c8e070e99187dd92ad
    ReactDOM.render( rootCompConstructor(), dom.document.getElementById( "joco" ) )
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
