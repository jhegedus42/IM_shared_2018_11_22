package demo.app

import demo.app.components.Main

import scala.scalajs.js.JSApp
import org.scalajs.dom.document
import CssSettings._
import org.scalajs.dom.raw.Element
import scalacss.ScalaCssReact._

object App extends JSApp {
  def main(): Unit = {
    println("elkezd futni a demo App")
    Styles.addToDocument()
    val e: Element = document.getElementById("rootComp")
    println("element="+e)
    Main().renderIntoDOM(e)
  }
}
