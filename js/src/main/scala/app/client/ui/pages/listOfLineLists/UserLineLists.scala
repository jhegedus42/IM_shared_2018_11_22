package app.client.ui.pages.listOfLineLists

import app.client.cache.{EntityCacheMap, EntityCacheVal}
import app.client.cache.wrapper.ReadAndWriteEntityRequestQue
import app.client.ui.pages.Types.Wrapped_CompConstr
import app.client.ui.pages.lineList.LineList_ReactComp
import app.client.ui.pages.main.root_children.materialUI_children.Pages.Page
import app.client.ui.pages.{LineListCompType, Props2Vanilla, Props2Wrapped, UserLineListsCompType}
import app.shared.data.model.User
import app.shared.data.ref.Ref
import japgolly.scalajs.react.ReactElement
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.{BackendScope, ReactComponentB}

object UserLineListsComp {
  import app.client.ui.pages.Types._

  type Prop = Ref[User]

  type Props = Props2Vanilla[Prop, UserLineListsCompType.type]

  class Backend($ : BackendScope[Props, Unit] ) {

    import japgolly.scalajs.react.vdom.prefix_<^._

    def render(props: Props ): ReactElement = {
      val c: EntityCacheMap = props.cache
      val u: EntityCacheVal[User] = props.cache.getEntity(props.ps)
      <.div(
        "cache:",
        <.br,
        pprint.apply( c.map ).plainText,
        "user uuid:",
        props.ps.uuid.toString,
        <.br,
        "user name:",
        {
          u.toString
        }

        //,
//        if(!u.isReady()) "UserLineList is loading"
//        else {
//          val r: Ref[User] = u.getValue.get.refVal.r
//          c.getEntity(r)
//        }
      )
    }

  }

  val compConstr: Vanilla_CompConstr[UserLineListsCompType.type, Prop] =
    ReactComponentB[Props](
      "wrapped " +
        "page component"
    ).backend[Backend]( new Backend( _ ) ).renderBackend.build

}

object UserLineListsWrapping {

  val que: ReadAndWriteEntityRequestQue = new ReadAndWriteEntityRequestQue()

  val wrapped_CC: Wrapped_CompConstr[UserLineListsCompType.type, UserLineListsComp.Prop] =
    que.wrapper.wrapRootPage[UserLineListsCompType.type, UserLineListsComp.Prop](UserLineListsComp.compConstr)


}
