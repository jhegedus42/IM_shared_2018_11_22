package app.client.ui.pages.lineDetail

import app.client.cache.{EntityCacheVal, Ready}
import app.client.rest.commands.forTesting.Helpers
import app.client.ui.pages.Props2Vanilla
import app.shared.data.model.LineText
//import app.client.rest.ClientRestAJAX
import app.client.ui.pages.LineDetailCompType
import app.client.ui.pages.Types.Vanilla_CompConstr
import app.shared.data.ref.{Ref, RefVal}

import scala.reflect.ClassTag
//import app.client.ui.pages.im.ImAutowireClient_circe
//import app.client.ui.pages.im.ImClientModel
//import app.client.ui.pages.lineDetail.LineDetailAction.Load_Line_for_LineDetailReactComp_from_Server

//import app.client.ui.pages.im.list.ListActions.{RefreshList, UpdateLine}

//import diode.data.Pot
//import diode.react.ModelProxy
//import diode.react.ReactPot._
import japgolly.scalajs.react.{Callback, ReactElement, ReactNode}
//import autowire._
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{BackendScope, ReactComponentB}

object LineDetail_ReactComp {
  type Prop = Ref[LineText]

  type Props = Props2Vanilla[Prop, LineDetailCompType.type]

//

  class Backend($ : BackendScope[Props, Unit] ) {

    def renderLine(lineRefVal: RefVal[LineText], p: Props ): ReactNode = {
      <.div(
//
        lineRefVal.toString //,
//          <.div(

//              <.form(^.method := "POST",
//                     ^.encType := "multipart/form-data",
//                     ^.action := "/fileFF")(<.input.file(^.name := "file"),
//                                            <.input.hidden(^.name := "uuid", ^.value := p.uuid.id.toString),
//                                            <.input.submit(^.value := "Submit")), {
//                val g: String => Unit = (s: String) => {
//                  (
//                      p.proxy.dispatchCB(UpdateLine(lineRefVal.lens(_.v.pl.text).set(s))) >>
//                        refreshCB(p)
//                  ).runNow()
//                }
//                // this did not work as def, Why the fuck not ? ? ?
//
//              }
//          )
      )
    }

    def render(p: Props ): ReactElement = {
      import monocle.macros.syntax.lens._
      val r:        Ref[LineText]      = p.ps
      val cacheVal: EntityCacheVal[LineText] = p.cache.getEntity(r)
      println( "trace1, in render, LineDetail_ReactComp, cacheVal=" + cacheVal )
      val refValOpt: Option[Ready[LineText]] = cacheVal.getValue

      <.div(
        "hello " + p.ps,
        <.br,
        cacheVal.toString,
        <.br,
        if (refValOpt.isDefined) {
          val rv: RefVal[LineText] = refValOpt.head.refVal
          val nw = rv.lens( _.v.title ).set( Some( "pina42" ) )
          import io.circe.generic.auto._
          import io.circe.{Decoder, Encoder} // do not uncomment this -- needed for deriveDecoder
          implicit val e = implicitly[Encoder[LineText]]
//          implicit val e=  ???
          implicit val d  = implicitly[Decoder[LineText]]
          implicit val ct = implicitly[ClassTag[LineText]]
          <.button( "set title to fuck", ^.onClick --> Callback {
            p.cache.updateEntity[LineText]( nw )( ct, d, e )
          } )
        } else {
          "not possible to update"
        },
        <.br,


        <.button( "reset server - needs a page reload after clicking this button", ^.onClick --> Callback {
          Helpers.resetServerToLabelOne()
        } )


      )
    }
  }

  val lineDetailConstructor: Vanilla_CompConstr[LineDetailCompType.type, Prop] = {
    ReactComponentB[Props]( "LineDetail" )
      .backend[Backend]( new Backend( _ ) )
      .renderBackend
//      .renderBackend[Backendackend]
//      .componentDidMount(scope => scope.backend.mounted(scope.props))
      .build
  }
//

}
