package experiments.cacheExperiments.components

import japgolly.scalajs.react.{CtorType, _}
import japgolly.scalajs.react.component.Scala.Component
import japgolly.scalajs.react.vdom.html_<^._

object RootComp {

  case class State(i:Int)
  case class Props(s:String)

  class Backend($ : BackendScope[Props, State] ) {


    val incCounter: CallbackTo[Unit] = $.modState(s=> s.copy(i= s.i + 1))

    val incCounterFiveSecLater: CallbackTo[Unit] = Callback{
      import scala.scalajs.js.timers._
      setTimeout(5000) { $.modState(s => s.copy(i = s.i + 1)).runNow() }
    }



    def render(state:State, props:Props) =
      <.div("State passed: ",
            state.toString,
            <.br,
            "Props passed:",
            props.toString,
            <.br,
            <.button( ^.onClick --> Callback.alert("The button was pressed!"), "Press me (alert)!"),
            <.br,
            <.button( ^.onClick --> incCounter, "Increment counter!"), // TASK: 061c2893_a517cd11
            <.br,
            <.button( ^.onClick --> Callback.log("button pressed"), "Press me (log)!"),
            <.br,
            <.button( ^.onClick --> incCounterFiveSecLater, "Increment counter 5 sec later!"), // a1b6f428_671bf29d
            )


    // dd029475_f9ddbea9
    // TODO in dynalist at
    // https://dynalist.io/d/1FvMKMOA-9w4G3dY9dXkBknT#z=qNJ7FMqu1lUdSxfTSB81W91x




  }

  //noinspection TypeAnnotation
  lazy val compConstructor =
    ScalaComponent
      .builder[Props]( "Cache Experiment" )
      .initialState(State(42))
      .renderBackend[Backend]
      .componentDidMount(
        x =>
          Callback(
            {
              println( "didmount " + x )
            }
        )
      )
      .componentDidUpdate(
        x =>
          Callback(
            {
              println( "did update " + x )

            }
        )
      )
      .build

}
