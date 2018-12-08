package demo.app

import demo.app.components.Main

import scala.scalajs.js.JSApp
import org.scalajs.dom.document
import CssSettings._
import org.scalajs.dom.raw.Element
import scalacss.ScalaCssReact._
import scalajsreact.template.routes.AppRouter

object App extends JSApp {
  def main(): Unit = {
    println("elkezd futni a demo App")
    Styles.addToDocument()
    val e: Element = document.getElementById("rootComp")
    val e2: Element = document.getElementById("rootComp2")
    println("element="+e)
    println("element="+e2)
    Main().renderIntoDOM(e)
    AppRouter.router().renderIntoDOM(e)
  }
}
