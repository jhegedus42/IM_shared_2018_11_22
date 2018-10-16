package app.client.ui.pages.listOfLineLists

import app.client.cache.entityCache.{EntityCacheMap, EntityCacheVal}
import app.client.cache.wrapper.CacheRoot
import app.client.ui.pages.Types.WrappedCompConstr
import app.client.ui.pages.lineList.LineList_ReactComp
import app.client.ui.pages.main.root_children.materialUI_children.Pages.Page

import app.client.ui.pages.{
  LineList_Wrappable_RootReactComp_PhantomType,
  PropsOfOuterComp,
  PropsOfInnerComp,
  UserLineLists_Wrappable_RootReactComp_PhantomType
}

import app.shared.data.model.User
import app.shared.data.ref.Ref
import app.shared.rest.views.viewsForDevelopingTheViewFramework.SumIntView_HolderObject.SumIntView_Res
import japgolly.scalajs.react.ReactElement
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.{BackendScope, ReactComponentB}

object UserLineListsComp {
  import app.client.ui.pages.Types._

  type Prop = Ref[User]

  type Props = PropsOfOuterComp[Prop, UserLineLists_Wrappable_RootReactComp_PhantomType.type]

  class Backend($ : BackendScope[Props, Unit] ) {

    import japgolly.scalajs.react.vdom.prefix_<^._

    def render(props: Props ): ReactElement = {
      val c: EntityCacheMap = props.entityCache
      // 442320ff08b24fd89244d566327a8cc4$4c99b1ca2b825dfc2e311c49f3572327a7c77e8d
      val u: EntityCacheVal[User] = props.entityCache.getEntity( props.ps )
      <.div(
        "cache:",
        <.br,
        pprint.apply( c.map ).plainText,
        "user uuid:",
        props.ps.uuid.toString,
        <.br,
        "user name:", {
          u.toString
        },
        <.br,
        "SumIntView 2+3:", {
          SumIntView_Res( 2 + 3 ).toString

          // ca343510efae45088b352ddb7231b4c6$4c99b1ca2b825dfc2e311c49f3572327a7c77e8d
          // props.

        }

        //
        //        if(!u.isReady()) "UserLineList is loading"
        //        else {
        //          val r: Ref[User] = u.getValue.get.refVal.r
        //          c.getEntity(r)
        //        }
      )
    }

  }

  val compConstr: NotYetWrappedCompConstr[UserLineLists_Wrappable_RootReactComp_PhantomType.type, Prop] =
    ReactComponentB[Props](
      "wrapped " +
        "page component"
    ).backend[Backend]( new Backend( _ ) ).renderBackend.build

}

object UserLineListsWrapping {

  val wrapperHolder: CacheRoot = new CacheRoot()

  val wrapped_CC
    : WrappedCompConstr[UserLineLists_Wrappable_RootReactComp_PhantomType.type, UserLineListsComp.Prop] =
    wrapperHolder.wrapper
      .wrapRootPage[UserLineLists_Wrappable_RootReactComp_PhantomType.type, UserLineListsComp.Prop](
        UserLineListsComp.compConstr
      )

}
