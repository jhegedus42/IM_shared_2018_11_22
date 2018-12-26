package experiments.cacheExperiments.components

import japgolly.scalajs.react.{CtorType, _}
import japgolly.scalajs.react.component.Scala.Component
import japgolly.scalajs.react.vdom.html_<^._

object RootComp {

  case class State(i:Int)
  case class Props(s:String)

  class Backend($ : BackendScope[Props, State] ) {

    def render(state:State, props:Props) =
      <.div("State passed: ",
            state.toString,
            <.br,
            "Props passed:",
            props.toString
            )

    // TODO push button => load Some Uuid Ref
    // TODO push button => print SumIntView x+y

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
