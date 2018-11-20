package app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator_depth1Components.lineDetail

import app.client.entityCache.entityCacheV1.{CacheState, RootReactCompConstr_Enhancer}
import app.client.entityCache.entityCacheV1.state.CacheStates.{EntityCacheVal, Ready}
import app.client.rest.commands.forTesting.Helpers
import app.client.entityCache.entityCacheV1.types.componentProperties.{
  D1Comp_Props,
  Depth1CompProps_With_RouterCtl,
  Depth2CompProps_ELI_D1CompProps_With_RouterCtl_With_EntityCache
}
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.{
  LineDetail_URL,
  URL_STr
}
import app.shared.data.model.LineText
import japgolly.scalajs.react.{ReactComponentU, TopNode}
import japgolly.scalajs.react.extra.router.RouterCtl
import app.client.entityCache.entityCacheV1.types.RootPageConstructorTypes.Depth2CompConstr_Alias
import app.shared.data.ref.{Ref, RefVal}

import scala.reflect.ClassTag

import japgolly.scalajs.react.{Callback, ReactElement, ReactNode}

import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{BackendScope, ReactComponentB}

object LineDetail_CompConstr_Holder {

  case class LineDetail_D1_CompProps_Wrapper(refLineText: Ref[LineText] ) extends D1Comp_Props

  /**
    *
    * Ezt ki csinalja ?
    * Ez hogyan jon letre ?
    * Ez minek kell ?
    * Ez mire lesz hasznalva ?
    * Ki fogja olvasni ?
    *
    */
  type Depth1CompProps_Alias = LineDetail_D1_CompProps_Wrapper

  private type Depth2CompProps_Alias =
    Depth2CompProps_ELI_D1CompProps_With_RouterCtl_With_EntityCache[Depth1CompProps_Alias, LineDetail_URL]

  def getCompConstr_For_dynRenderR_In_Router(reactCompWrapper: RootReactCompConstr_Enhancer ): (
      LineDetail_URL,
      RouterCtl[URL_STr]
  ) => ReactComponentU[Depth1CompProps_With_RouterCtl[Depth1CompProps_Alias],
                       CacheState, //TODO ezt a cache-state-es baromsagot atirni RW access provider-re
                       _,
                       TopNode] = {
    ( x: LineDetail_URL, r: RouterCtl[URL_STr] ) =>
      {
        val rlt: Ref[LineText] = Ref.makeWithUUID( x.idOfLine )
        val d1c = LineDetail_D1_CompProps_Wrapper( rlt )
        LineDetailWrapping( reactCompWrapper ).constructor_used_by_the_parent_component(
          Depth1CompProps_With_RouterCtl( d1c, r )
        )
      }
  }

//
  class Backend_LineDetail(backendScope: BackendScope[Depth2CompProps_Alias, Unit] ) {

    def renderLine(lineRefVal: Depth1CompProps_Alias, depth2CompProps: Depth2CompProps_Alias ): ReactNode = {
      <.div(
//
        lineRefVal.toString
//,
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

    def render(depth2CompProps: Depth2CompProps_Alias ): ReactElement = {
      import monocle.macros.syntax.lens._
      val r:        Ref[LineText]            = depth2CompProps.depth1CompProps.refLineText
      val cacheVal: EntityCacheVal[LineText] = depth2CompProps.entityCache.getEntity( r )
      println( "trace1, in render, LineDetail_ReactComp, cacheVal=" + cacheVal )
      val refValOpt: Option[Ready[LineText]] = cacheVal.getValue

      <.div(
        "props that the render got: ",
        <.br,
        depth2CompProps.depth1CompProps.toString,
        <.br,
        "props that the render got printed with pretty print: ",
        <.br,
        pprint.apply( depth2CompProps.depth1CompProps ).toString(),
        <.br,
        s"the value of the cache for $r:",
        <.br,
        cacheVal.toString,
        <.br,
        if (refValOpt.isDefined) {

          val rv: RefVal[LineText] = refValOpt.head.refVal

          val nw = rv.lens( _.v.title ).set( "pina42" )
          import io.circe.generic.auto._
          import io.circe.{Decoder, Encoder} // do not uncomment this -- needed for deriveDecoder

          implicit val e = implicitly[Encoder[LineText]]
//          implicit val e=  ???

          implicit val d = implicitly[Decoder[LineText]]

          implicit val ct = implicitly[ClassTag[LineText]]

          <.button( "set title to fuck", ^.onClick --> Callback {
            depth2CompProps.entityCache.updateEntity[LineText]( nw )( ct, d, e )
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

  val lineDetail_Depth2CompConstructor: Depth2CompConstr_Alias[LineDetail_URL, Depth1CompProps_Alias] = {
    ReactComponentB[Depth2CompProps_Alias]( "LineDetail" )
      .backend[Backend_LineDetail]( new Backend_LineDetail( _ ) )
      .renderBackend
//      .renderBackend[Backendackend]
//      .componentDidMount(scope => scope.backend.mounted(scope.props))
      .build
  }
//

}
