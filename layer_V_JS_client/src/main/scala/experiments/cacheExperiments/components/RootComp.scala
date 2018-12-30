package experiments.cacheExperiments.components

import app.shared.SomeError_Trait
import app.shared.data.model.LineText
import app.shared.data.ref.{Ref, RefVal}
import app.testHelpersShared.data.TestEntities
import experiments.cacheExperiments.cache.Cache
import japgolly.scalajs.react.{CtorType, _}
import japgolly.scalajs.react.component.Scala.Component
import japgolly.scalajs.react.component.builder.Lifecycle
import japgolly.scalajs.react.vdom.html_<^._
import scalaz.\/

import scala.concurrent.{ExecutionContextExecutor, Future}

object RootComp {

  case class State(i: Int, lineTextOption: Option[RefVal[LineText]] )

  case class Props(s: String )

  def getLineRefValOptionFromCacheAsString():String = Cache.read().toString

  class Backend($ : BackendScope[Props, State] ) {

    val incCounter: CallbackTo[Unit] = $.modState( s => s.copy( i = s.i + 1 ) )

    val incCounterFiveSecLater: CallbackTo[Unit] = Callback {
      import scala.scalajs.js.timers._
      setTimeout( 5000 ) { $.modState( s => s.copy( i = s.i + 1 ) ).runNow() }
    }

    val incCounterFiveSecLater_CalledFromComponentDidMount: CallbackTo[Unit] = Callback { // for TASK_e66038a2_ece05d8e
      import scala.scalajs.js.timers._
      println("incCounterFiveSecLater_CalledFromComponentDidMount was called")
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
            val lt:  \/[SomeError_Trait, RefVal[LineText]] = x
            val res: Option[RefVal[LineText]]              = lt.toOption
            $.modState( s => s.copy( lineTextOption = res ) ).runNow()
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
        <.button( ^.onClick --> Callback.alert( "The button was pressed!" ), "Press me (alert)!" ),
        <.br,
        <.button( ^.onClick --> incCounter, "Increment counter!" ), // TASK: 061c2893_a517cd11
        <.br,
        <.button( ^.onClick --> Callback.log( "button pressed" ), "Press me (log)!" ),
        <.br,
        <.button( ^.onClick --> incCounterFiveSecLater, "Increment counter 5 sec later!" ), // TASK_a1b6f428_671bf29d
        <.br,
        <.button( ^.onClick --> fetchDataFromServer, "Fetch data from server." ), // TASK_fa6672bc_9bb672a8
        <.br,
        "Cache contains:",
        <.br,
        getLineRefValOptionFromCacheAsString()
      )
    // dd029475_f9ddbea9
    // TODO in dynalist at
    // https://dynalist.io/d/1FvMKMOA-9w4G3dY9dXkBknT#z=qNJ7FMqu1lUdSxfTSB81W91x

  }

  //noinspection TypeAnnotation
  lazy val compConstructor =
    ScalaComponent
      .builder[Props]( "Cache Experiment" )
      .initialState( State( 42, None ) )
      .renderBackend[Backend]
      .componentDidMount(
        (x: Lifecycle.Base[Props, State, Backend]) => {
          println("component did mount")
          x.backend.incCounterFiveSecLater_CalledFromComponentDidMount
        }
        // for TASK_e66038a2_ece05d8e
        // here we can pass the re-render triggering function to the cache
      )
      .componentDidUpdate(
        x =>
          Callback(
            {
              println( "component did update " + x )

            }
        )
      )
      .build

}
