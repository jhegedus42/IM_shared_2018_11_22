package experiments.cacheExperiments

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object MainComp {
    def  apply = ScalaComponent // TODO add state here so that something can be changed to cause a re-render
                      .builder[String]( "HelloMessage" )
                      .render(
        $ => <.div( "Hello ", $.props )
        // TODO println list of line text-s
      )
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
