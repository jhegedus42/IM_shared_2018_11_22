package experiments.cacheExperiments.components

import app.shared.SomeError_Trait
import app.shared.data.model.LineText
import app.shared.data.ref.{Ref, RefVal}
import app.shared.data.utils.PrettyPrint
import app.testHelpersShared.data.TestEntities
import experiments.cacheExperiments.cache
import experiments.cacheExperiments.cache.{AJAXReqInFlightMonitor, CacheFaszad, ReRenderTriggerer}
import japgolly.scalajs.react.{CtorType, _}
import japgolly.scalajs.react.component.Scala.Component
import japgolly.scalajs.react.component.builder.Lifecycle
import japgolly.scalajs.react.vdom.html_<^._
import scalaz.\/

import scala.concurrent.{ExecutionContextExecutor, Future}

object RootComp {

  case class State(i: Int,lineTextOption: Option[RefVal[LineText]])

  case class Props(s: String )

  /**
    * Ez egyaltalan minek van itt ?
    * Hogy tesztelje a kess-t.
    * @return
    */
  def getLineRefValOptionFromCacheAsString: String = {
    val ref: Ref[LineText] = Ref.makeWithUUID[LineText]( TestEntities.refValOfLineV0.r.uuid )
    val res: String = CacheFaszad.readLineText(ref).toString
    PrettyPrint.prettyPrint(res)
    res
  }

  class Backend($ : BackendScope[Props, State] ) {

    val incCounter: CallbackTo[Unit] = $.modState( s => s.copy( i = s.i + 1 ) )

    val triggerReRenderAndIncCounter: CallbackTo[Unit] =
      $.modState( s => s.copy( i = s.i + 1 ) )

    val incCounterFiveSecLater: CallbackTo[Unit] = Callback {
      import scala.scalajs.js.timers._
      setTimeout( 5000 ) { $.modState( s => s.copy( i = s.i + 1 ) ).runNow() }
    }

    val incCounterFiveSecLater_CalledFromComponentDidMount: CallbackTo[Unit] =
      Callback { // for TASK_e66038a2_ece05d8e
        import scala.scalajs.js.timers._
        println( "incCounterFiveSecLater_CalledFromComponentDidMount was called" )
        setTimeout( 5000 ) { $.modState( s => s.copy( i = s.i + 1 ) ).runNow() }
      }

    val fetchDataFromServer: CallbackTo[Unit] =
      Callback {
        implicit def executionContext: ExecutionContextExecutor =
          scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
        import app.client.rest.commands.generalCRUD.GetEntityAJAX.getEntity
        import io.circe.generic.auto._
        val ref: Ref[LineText] = Ref.makeWithUUID[LineText]( TestEntities.refValOfLineV0.r.uuid )
        val res: Future[Unit] = getEntity[LineText]( ref ).map(
          x => {
            println( s"az entity visszavage $x" )
            val lt:  RefVal[LineText] = x
            $.modState( s => s.copy( lineTextOption = Some(lt) ) ).runNow()
          }
        )
      }

    def render(state: State, props: Props ) =
      <.div(
        "State passed: ",
        state.toString,
        <.br,
        "Props passed:",
        props.toString,
        <.br,
        <.button( ^.onClick --> fetchDataFromServer, "Fetch data from server." ), // TASK_fa6672bc_9bb672a8
        <.br,
        "Cache contains:",
        <.br,
        getLineRefValOptionFromCacheAsString
      )

    // dd029475_f9ddbea9
    // completed TASK described in dynalist at
    // https://dynalist.io/d/1FvMKMOA-9w4G3dY9dXkBknT#z=qNJ7FMqu1lUdSxfTSB81W91x

  }

  def toBeCalledByComponentDidMount(x: Lifecycle.Base[Props, State, Backend] ): CallbackTo[Unit] =
    Callback {
      println( "component did mount" )

      val reRenderTriggerer =
        ReRenderTriggerer( _ => {
          println( "we trigger now a re-render (because the reRenderTriggerer was executed." )
          x.backend.incCounter.runNow() // WE TRIGGER HERE A REAL RE-RENDER
          println( "we have just increased the counter in the component" )
        } )

               AJAXReqInFlightMonitor.reRenderTriggerer = Some(reRenderTriggerer)
    }

  //noinspection TypeAnnotation
  lazy val compConstructor =
    ScalaComponent
      .builder[Props]( "Cache Experiment" )
      .initialState( State( 42, None ) )
      .renderBackend[Backend]
      .componentDidMount(toBeCalledByComponentDidMount(_))
      .componentDidUpdate( x => Callback( println( "component did update " + x ) ) )
      .build

}
