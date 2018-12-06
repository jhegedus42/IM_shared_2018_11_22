package app.client.ui_v2

import japgolly.scalajs.react.ReactComponentC.ReqProps
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{BackendScope, ReactComponentB, ReactElement, TopNode}


object D0_SimpleComp {

  type CompConst = ReqProps[String, Unit, _, TopNode]

  class BackendSimpleComp(backendScope: BackendScope[String, Unit] ) {

    def render(depth2CompProps: String): ReactElement = {

      <.div(
             "props that the render got: ",
             <.br,
              depth2CompProps,
             <.br,
             "props that the render got printed with pretty print: ",
             <.br,
             <.br
           )
    }
  }

  val simpleCompConstr: CompConst = {
    ReactComponentB[String]( "LineDetail" )
    .backend[BackendSimpleComp](new BackendSimpleComp(_))
    .renderBackend
    .build
  }
  //

}
