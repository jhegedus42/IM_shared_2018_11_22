package app.client.ui.pages.lineList

import java.util.concurrent.ThreadLocalRandom

import app.client.cache.{CacheMap, EntityCacheVal, Loaded}
import app.client.rest.commands.forTesting.Helpers
import app.client.ui.pages.{LineListCompType, Props2Vanilla}
import app.shared.model.entities.LineText
import app.shared.model.ref.Ref
import fansi.Str
import japgolly.scalajs.react.ReactElement

//import app.client.ui.pages.im.ImAutowireClient_circe
import scala.concurrent.Future
//import app.client.ui.pages.im.ImClientModel

//import app.client.ui.pages.im.list.ListActions.{CreateNewLineAtServer, MoveLine, RefreshList}

//import diode.react.ModelProxy
//import diode.react.ReactPot._

import japgolly.scalajs.react.{BackendScope, ReactComponentB}

object LineList_ReactComp {
  import app.client.ui.pages.Types._

  type Prop  = Unit
  type Props = Props2Vanilla[Prop, LineListCompType.type]

  class Backend($ : BackendScope[Props, Unit] ) {

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

    def render(props: Props ): ReactElement = {
      val c: CacheMap = props.cache

      val ref: Ref[LineText] =
        Ref.makeWithUUID[LineText]( "4ce6fca0-0fd5-4197-a946-90f5e7e00d9d" ) // right
      val e: EntityCacheVal[LineText] = c.getEntity(ref)

      val ref2: Ref[LineText] =
        Ref.makeWithUUID[LineText]( "4ce6fca0-0fd5-4197-a946-90f5e7e00d9e" ) // right
      val e2: EntityCacheVal[LineText] = c.getEntity(ref2)

      val s: Str = pprint.apply( Seq( 1, 2, 3 ) )
      val s2 = s.plainText

//      def pp[T](t:T): String = pprint.apply(t).plainText

//      <.div(^.whiteSpace:="pre", "joco\n moco")
      import japgolly.scalajs.react.{Callback, ReactElement, ReactNode}

      <.div(
        ^.whiteSpace := "pre",
        "cache:",
        <.br,
        pprint.apply( c.map ).plainText,
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
        <.button( " 42 reset server - needs a page reload after clicking this button", ^.onClick --> Callback {
          Helpers.resetServerToLabelThree()
        } )
      )




    }

  }

  val LineListCompBuilder: Vanilla_CompConstr[LineListCompType.type, Unit] =
    ReactComponentB[Props](
      "wrapped " +
        "page component"
    ).backend[Backend]( new Backend( _ ) ).renderBackend.build

}
