package app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator.listOfLineLists

import app.client.entityCache.entityCacheV1.types.componentProperties.Props_Of_Depth2Comp
import app.client.entityCache.entityCacheV1.CacheState
import app.client.entityCache.entityCacheV1.types.CacheStates.EntityCacheVal
import app.shared.data.model.User
import app.shared.data.ref.Ref
import app.shared.rest.views.viewsForDevelopingTheViewFramework.SumIntView_HolderObject.SumIntView_Res
import japgolly.scalajs.react.{BackendScope, ReactComponentB, ReactElement}

object UserLineListsComp {
  import app.client.entityCache.entityCacheV1.types.RootPageConstructorTypes._

  type Prop = Ref[User]

  type Props = Props_Of_Depth2Comp[Prop, ListOfLineListsOfAGivenUser_Page]

  class Backend($ : BackendScope[Props, Unit] ) {

    import japgolly.scalajs.react.vdom.prefix_<^._

    def render(props: Props ): ReactElement = {
      val c: CacheState = props.entityCache
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
          // TODO - let's put the result into here ....
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

  val compConstr: Depth2CompConstr[ListOfLineListsOfAGivenUser_Page, Prop] =
    ReactComponentB[Props](
      "wrapped " +
        "page component"
    ).backend[Backend]( new Backend( _ ) ).renderBackend.build

}


