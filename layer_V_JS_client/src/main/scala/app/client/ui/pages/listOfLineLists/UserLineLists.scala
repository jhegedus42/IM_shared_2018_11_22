package app.client.ui.pages.listOfLineLists

import app.client.entityCache.entityCacheV1.types.PropsOfWrappedComp
import app.client.entityCache.entityCacheV1.types.RootPageConstructorTypes.WrappedRootPageCompConstr
import app.client.entityCache.entityCacheV1.types.Vanilla_RootReactComponent_PhantomTypes.UserLineLists_Vanilla_RootReactComp_PhantomType
import app.client.entityCache.entityCacheV1.{EntityCacheVal, EntityReaderWriter, ReactCompWrapper}
import app.shared.data.model.User
import app.shared.data.ref.Ref
import app.shared.rest.views.viewsForDevelopingTheViewFramework.SumIntView_HolderObject.SumIntView_Res
import japgolly.scalajs.react.{BackendScope, ReactComponentB, ReactElement}

object UserLineListsComp {
  import app.client.entityCache.entityCacheV1.types.RootPageConstructorTypes._

  type Prop = Ref[User]

  type Props = PropsOfWrappedComp[Prop, UserLineLists_Vanilla_RootReactComp_PhantomType.type]

  class Backend($ : BackendScope[Props, Unit] ) {

    import japgolly.scalajs.react.vdom.prefix_<^._

    def render(props: Props ): ReactElement = {
      val c: EntityReaderWriter = props.entityCache
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

  val compConstr: VanillaRootPageCompConstr[UserLineLists_Vanilla_RootReactComp_PhantomType.type, Prop] =
    ReactComponentB[Props](
      "wrapped " +
        "page component"
    ).backend[Backend]( new Backend( _ ) ).renderBackend.build

}

case class UserLineListsWrapping(wrapper : ReactCompWrapper) {

//  val wrapperHolder: CacheRoot = new CacheRoot()

  val wrapped_CC
    : WrappedRootPageCompConstr[UserLineLists_Vanilla_RootReactComp_PhantomType.type, UserLineListsComp.Prop] =
    wrapper
    .createWrappedRootPageCompConstructor[UserLineLists_Vanilla_RootReactComp_PhantomType.type,
      UserLineListsComp.Prop](
        UserLineListsComp.compConstr
                                                                                                                       )

}
