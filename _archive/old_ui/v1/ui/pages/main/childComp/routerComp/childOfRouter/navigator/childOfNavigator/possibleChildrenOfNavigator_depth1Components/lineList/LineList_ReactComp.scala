package app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator_depth1Components.lineList

import java.util.concurrent.ThreadLocalRandom

import app.client.entityCache.entityCacheV1.CacheState
import app.client.entityCache.entityCacheV1.state.CacheStates.EntityCacheVal
import app.client.entityCache.entityCacheV1.types.RootPageConstructorTypes
import app.client.entityCache.entityCacheV1.types.componentProperties.{D1Comp_Props, RouterToD1Props, Depth2CompProps_ELI_D1CompProps_With_RouterCtl_With_EntityCache}
import app.client.rest.commands.forTesting.Helpers
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.LineList_Page
import app.shared.data.model.LineText
import app.shared.data.ref.Ref
import fansi.Str
import japgolly.scalajs.react.ReactElement

//import app.client.ui.pages.im.ImAutowireClient_circe
import scala.concurrent.Future
//import app.client.ui.pages.im.ImClientModel

//import app.client.ui.pages.im.list.ListActions.{CreateNewLineAtServer, MoveLine, RefreshList}

//import diode.react.ModelProxy
//import diode.react.ReactPot._

import japgolly.scalajs.react.{BackendScope, ReactComponentB}
case object NoProps extends D1Comp_Props

object LineList_ReactComp {
  import app.client.entityCache.entityCacheV1.types.RootPageConstructorTypes._

  type Prop = NoProps.type

  class Backend(
      $ : BackendScope[Depth2CompProps_ELI_D1CompProps_With_RouterCtl_With_EntityCache[Prop, LineList_Page],
                       Unit]) {

    def r: Int = {
      val min: Int = 1e6.toInt
      ThreadLocalRandom.current().nextInt( min, 5 * min )
    }

    def sf: Future[String] = {
      println( "bla1 " )
      ???
    }

//    def f =
//      (p: LLRC_Props) => (ic: ListReorderService) =>
//        Callback({println("sort ended :  ") })
//    // update state = update + refresh
//    // where do we store state ?  - in the component
//    // what is the state ? - Option[List[Ref[Line]]]
//
//    def reorderList(items: List[RefVal[Line]], p: LLRC_Props): ReactNode =
//      ReorderList.sortableList(
//          SortableContainer.Props(
//              onSortEnd = f(p),
//              useDragHandle = true,
//              helperClass = "react-sortable-handler",
//              useWindowAsScrollContainer = false
//          ))((items, p))

    import japgolly.scalajs.react.vdom.prefix_<^._

    def render(
        props: Depth2CompProps_ELI_D1CompProps_With_RouterCtl_With_EntityCache[Prop, LineList_Page]
      ): ReactElement = {
      val c: CacheState = props.entityCache

      val ref: Ref[LineText] =
        Ref.makeWithUUID[LineText]( "4ce6fca0-0fd5-4197-a946-90f5e7e00d9d" ) // right
      val e: EntityCacheVal[LineText] = c.getEntity( ref )

      val ref2: Ref[LineText] =
        Ref.makeWithUUID[LineText]( "4ce6fca0-0fd5-4197-a946-90f5e7e00d9e" ) // right
      val e2: EntityCacheVal[LineText] = c.getEntity( ref2 )

      val s: Str = pprint.apply( Seq( 1, 2, 3 ) )
      val s2 = s.plainText

//      def pp[T](t:T): String = pprint.apply(t).plainText

//      <.div(^.whiteSpace:="pre", "joco\n moco")
      import japgolly.scalajs.react.Callback

      <.div(
        ^.whiteSpace := "pre",
        "cache:",
        <.br,
//        pprint.apply( c.map ).plainText,
        <.br,
        " entity: ",
        <.br,
        e.toString,
        <.br,
        " entity fail:",
        <.br,
        e2.toString,
        <.br,
        s2,
        <.button(
          " 42 reset server - needs a page reload after clicking this button",
          ^.onClick --> Callback {
            Helpers.resetServerToLabelThree()
          }
        )
      )

    }

  }

  val LineListCompBuilder: Depth2CompConstr_Alias[LineList_Page, NoProps.type] =
    ReactComponentB[Depth2CompProps_ELI_D1CompProps_With_RouterCtl_With_EntityCache[Prop, LineList_Page]](
      "wrapped " +
        "page component"
    ).backend[Backend]( new Backend( _ ) ).renderBackend.build

}

import app.client.entityCache.entityCacheV1.D2toD1Transformer
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.{LineList_Page, URL_STr}
import japgolly.scalajs.react.ReactElement
import japgolly.scalajs.react.extra.router.RouterCtl

/**
  * Created by joco on 06/01/2018.
  *
  */
case class LineListWrapping(wrapper: D2toD1Transformer ) {


//  val que: CacheRoot = new CacheRoot()

  val wLL: RootPageConstructorTypes.Depth1CompConstrWrapper[LineList_Page, NoProps.type] =
    wrapper.trD2toD1[LineList_Page, NoProps.type](
      LineList_ReactComp.LineListCompBuilder
    )

  val mk_wLL: ( RouterCtl[URL_STr] ) => ReactElement = (r: RouterCtl[URL_STr]) =>
    wLL.d1Constr(RouterToD1Props(NoProps, r))

}
