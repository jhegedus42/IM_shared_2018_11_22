package experiments.cacheExperiments

import app.client.rest.commands.generalCRUD.GetEntityAJAX.getEntity
import app.shared.data.model.LineText
import app.shared.data.ref.{Ref, RefDyn}
import app.testHelpersShared.data.TestEntities
import experiments.cacheExperiments.components.RootComp
import experiments.cacheExperiments.css.AppWithCacheCSS
import org.scalajs.dom.document
import org.scalajs.dom.raw.Element

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.html_<^._



@JSExport( "ExperimentalAppWithCache" )
object ExperimentalAppWithCache extends js.JSApp {
  implicit def executionContext: ExecutionContextExecutor =
    scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

  @JSExport
  def main(): Unit = {

    AppWithCacheCSS.load()
    import slogging._
    LoggerConfig.factory = PrintLoggerFactory()
    LoggerConfig.level   = LogLevel.TRACE

    import io.circe.generic.auto._

    val ref: Ref[LineText] =
      Ref.makeWithUUID[LineText]( TestEntities.refValOfLineV0.r.uuid )

    val res: Future[Unit] = getEntity[LineText]( ref ).map( x => println( s"az entity visszavage $x" ) )

    val e: Element = document.getElementById( "rootComp" )

    val rootComp= RootComp.compConstructor(RootComp.Props("These are the props"))
        rootComp.renderIntoDOM( e )

    import scala.scalajs.js.timers._

    setTimeout(1000) {
                       // rootComp //TODO set state....
                       println("1s has elapsed")
                     }
  }
}
