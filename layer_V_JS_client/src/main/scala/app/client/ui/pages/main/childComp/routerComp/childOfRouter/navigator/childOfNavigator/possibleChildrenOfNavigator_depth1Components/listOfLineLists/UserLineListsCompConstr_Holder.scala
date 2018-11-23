package app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator_depth1Components.listOfLineLists

import app.client.entityCache.entityCacheV1.D2toD1Transformer
import app.client.entityCache.entityCacheV1.types.componentProperties.{
  D1Comp_Props,
  RouterToD1Props,
  Depth2CompProps_ELI_D1CompProps_With_RouterCtl_With_EntityCache
}
import app.client.entityCache.entityCacheV1.state.CacheStates.EntityCacheVal
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.{
  LineListsOfUser_URL,
  URL_STr
}
import app.shared.data.model.User
import app.shared.data.ref.Ref
import app.shared.rest.views.viewsForDevelopingTheViewFramework.SumIntView_HolderObject.SumIntView_Res
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.{BackendScope, ReactComponentB, ReactElement}

object UserLineListsCompConstr_Holder {
  import app.client.entityCache.entityCacheV1.types.RootPageConstructorTypes._

  case class D1CompProps_UserLineList(refUser: Ref[User] ) extends D1Comp_Props

  type D2CompProps_LocTAlias =
    Depth2CompProps_ELI_D1CompProps_With_RouterCtl_With_EntityCache[D1CompProps_UserLineList,
                                                                    LineListsOfUser_URL]

  class UserLineList_Backend(backendScope: BackendScope[D2CompProps_LocTAlias, Unit] ) {

    import japgolly.scalajs.react.vdom.prefix_<^._

    def render(depth2CompProps: D2CompProps_LocTAlias ): ReactElement = {
      // 442320ff08b24fd89244d566327a8cc4$4c99b1ca2b825dfc2e311c49f3572327a7c77e8d
      val u: EntityCacheVal[User] =
        depth2CompProps.entityCache.getEntity( depth2CompProps.depth1CompProps.refUser )
      <.div(
        "cache:",
        <.br,
        //        pprint.apply( props.entityCache.map ).plainText,
        "user uuid:",
        depth2CompProps.depth1CompProps.refUser.uuid.toString,
        <.br,
        "user name:", {
          u.toString
        },
        <.br,
        "SumIntView 2+3:", {
          SumIntView_Res( 2 + 3 ).toString
          // dd60dc57_fe44c5caca343510efae45088b352ddb7231b4c6$4c99b1ca2b825dfc2e311c49f3572327a7c77e8d
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

  // TODO `Depth2CompConstr_Alias` helyebe vmi case class-t kene tenni
  val compConstr: Depth2CompConstr_Alias[LineListsOfUser_URL, D1CompProps_UserLineList] =
    ReactComponentB[D2CompProps_LocTAlias](
      "wrapped " +
        "page component"
    ).backend[UserLineList_Backend]( new UserLineList_Backend( _ ) ).renderBackend.build

  // TODO below, create a case class wrapper for
  // this funny type below
  // something like `case class functionNeeded_By_dynRenderR`
  def getFunctionNeededForDynRenderR(
      d2tod1: D2toD1Transformer
    ): ( LineListsOfUser_URL, RouterCtl[URL_STr] ) => ReactElement = {
    ( u: LineListsOfUser_URL, r: RouterCtl[URL_STr] ) =>
      {

        val refU: Ref[User] = Ref.makeWithUUID[User]( u.id_ofUser )

        val d1_comp = D1CompProps_UserLineList( refU )

        val f: RouterToD1Props[D1CompProps_UserLineList] =
          RouterToD1Props(d1_comp, r)

        val res: Depth1CompConstrWrapper[LineListsOfUser_URL, D1CompProps_UserLineList] =
          UserLineListsWrapping( d2tod1 ).wrapped_CC
        res.d1Constr (f)
      }
  }

}
