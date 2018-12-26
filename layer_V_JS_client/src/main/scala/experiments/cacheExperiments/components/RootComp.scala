package experiments.cacheExperiments.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object RootComp {

  case class State(i:Int)
  case class Props(s:String)

  class Backend($ : BackendScope[Props, State] ) {

//    def render(state : State) =
//      <.div("State passed: ", state.toString)
    def render(state:State, props:Props) =
      <.div("State passed: ",
            state.toString,
            "Props passed:",
            props.toString
            )

  }

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
              //TODO put ajax call here, which updates the component when it comes back
            }
        )
      )
      .componentDidUpdate(
        x =>
          Callback(
            {
              println( "did update " + x )
              //TODO put ajax call here, which updates the component when it comes back

            }
        )
      )
      .build

}
