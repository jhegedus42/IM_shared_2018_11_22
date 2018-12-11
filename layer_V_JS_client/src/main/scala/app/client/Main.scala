package app.client

import app.client.rest.commands.generalCRUD.GetEntityAJAX.getEntity
import app.client.ui.css.AppCSS
import app.client.ui.routes.AppRouter
import app.shared.data.model.LineText
import app.shared.data.ref.Ref
import app.testHelpersShared.data.TestEntities
import org.scalajs.dom.document
import org.scalajs.dom.raw.Element

import scala.concurrent.ExecutionContextExecutor
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport

@JSExport( "Main" )
object Main extends js.JSApp {
  implicit def executionContext: ExecutionContextExecutor =
    scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

  @JSExport
  def main(): Unit = {


    import slogging._
    LoggerConfig.factory = PrintLoggerFactory()
    LoggerConfig.level   = LogLevel.TRACE

    import io.circe.generic.auto._
    val ref: Ref[LineText] =
      Ref.makeWithUUID[LineText]( TestEntities.refValOfLineV0.r.uuid )

    getEntity[LineText]( ref ).map( x => println(s"az entity visszavage $x"))

      AppCSS.load
    val e: Element = document.getElementById("rootComp")
    println("element="+e)
    AppRouter.router().renderIntoDOM(e)

  }
}
