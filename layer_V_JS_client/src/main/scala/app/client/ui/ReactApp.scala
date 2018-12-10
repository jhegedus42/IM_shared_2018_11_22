package app.client.ui


import org.scalajs.dom.document
import org.scalajs.dom.raw.Element
import app.client.ui.css.AppCSS
import app.client.ui.routes.AppRouter

import scala.scalajs.js.JSApp

object ReactApp extends JSApp {
  def main(): Unit = {
    println("elkezd futni a demo App")
    AppCSS.load
    val e: Element = document.getElementById("rootComp")
    println("element="+e)
    AppRouter.router().renderIntoDOM(e)
  }
}






