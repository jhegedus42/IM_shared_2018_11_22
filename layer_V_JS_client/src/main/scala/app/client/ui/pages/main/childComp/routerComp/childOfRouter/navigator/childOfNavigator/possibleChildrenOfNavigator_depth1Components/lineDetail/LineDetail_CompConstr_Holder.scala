package app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator_depth1Components.lineDetail

import app.client.entityCache.entityCacheV1.{CacheState, D2toD1Transformer}
import app.client.entityCache.entityCacheV1.state.CacheStates.{EntityCacheVal, Ready}
import app.client.entityCache.entityCacheV1.types.RootPageConstructorTypes
import app.client.rest.commands.forTesting.Helpers
import app.client.entityCache.entityCacheV1.types.componentProperties.{
  D1Comp_Props,
  RouterToD1Props,
  Depth2CompProps_ELI_D1CompProps_With_RouterCtl_With_EntityCache
}
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.{
  LineDetail_URL,
  URL_STr,
  possibleChildrenOfNavigator_depth1Components
}
import app.shared.data.model.LineText
import japgolly.scalajs.react.{ReactComponentU, TopNode}
import japgolly.scalajs.react.extra.router.RouterCtl
import app.client.entityCache.entityCacheV1.types.RootPageConstructorTypes.Depth2CompConstr_Alias
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator_depth1Components.lineDetail
import app.shared.data.ref.{Ref, RefVal}

import scala.reflect.ClassTag
import japgolly.scalajs.react.{Callback, ReactElement, ReactNode}
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{BackendScope, ReactComponentB}

object LineDetail_CompConstr_Holder {

  case class LineDetail_D1_Props(refLineText: Ref[LineText] ) extends D1Comp_Props //TODO - ide tenni az URL-t, hogy kihez tartozik

  /**
    *
    * Ezt ki csinalja ?
    * Ez hogyan jon letre ?
    * Ez minek kell ?
    * Ez mire lesz hasznalva ?
    * Ki fogja olvasni ?
    *
    */
  private type Depth2CompProps_Alias =
    Depth2CompProps_ELI_D1CompProps_With_RouterCtl_With_EntityCache[LineDetail_D1_Props,
                                                                    LineDetail_URL]

  object F {
    type Par = ( LineDetail_URL, RouterCtl[URL_STr] )

    type Props = RouterToD1Props[LineDetail_D1_Props]

    type Res = ReactComponentU[Props, CacheState, _, TopNode]
    //TODO ezt a cache-state-es baromsagot atirni RW access provider-re
  }

  /**
    * @param trD2toD1 Takes a D2 comp constructor, adds the cache access and then
    *                 returns a D1 comp constructor.
    * @return
    */
  def getCompConstr_For_dynRenderR_In_Router(trD2toD1: D2toD1Transformer ): F.Par => F.Res = {

    val res: F.Par => F.Res =
      ( x: LineDetail_URL, r: RouterCtl[URL_STr] ) => {
        val rlt: Ref[LineText] = Ref.makeWithUUID( x.idOfLine )
        val d1c = LineDetail_D1_Props(rlt)

        val f = RouterToD1Props(d1c, r)
        val g: F.Res =
          LineDetailWrapping( trD2toD1 ).constructor_used_by_the_parent_component
        g
      }
    res
  }

//
  class Backend_LineDetail(backendScope: BackendScope[Depth2CompProps_Alias, Unit] ) {

    def renderLine(
                    lineRefVal:      LineDetail_D1_Props,
                    depth2CompProps: Depth2CompProps_Alias
      ): ReactNode = {
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

  val lineDetail_Depth2CompConstructor
    : Depth2CompConstr_Alias[LineDetail_URL, LineDetail_D1_Props] = {
    ReactComponentB[Depth2CompProps_Alias]( "LineDetail" )
      .backend[Backend_LineDetail]( new Backend_LineDetail( _ ) )
      .renderBackend
//      .renderBackend[Backendackend]
//      .componentDidMount(scope => scope.backend.mounted(scope.props))
      .build
  }
//

}
