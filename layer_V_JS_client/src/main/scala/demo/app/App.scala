package demo.app

import demo.app.components.Main
import org.scalajs.dom.document
import org.scalajs.dom.raw.Element
import scalajsreact.template.css.AppCSS
import scalajsreact.template.routes.AppRouter

import scala.scalajs.js.JSApp

object App extends JSApp {
  def main(): Unit = {
    println("elkezd futni a demo App")
//    Styles.addToDocument()
    val e: Element = document.getElementById("rootComp")
    val e2: Element = document.getElementById("rootComp2")
    AppCSS.load
    println("element="+e)
    println("element="+e2)
    Main().renderIntoDOM(e)
    AppRouter.router().renderIntoDOM(e)
  }
}
