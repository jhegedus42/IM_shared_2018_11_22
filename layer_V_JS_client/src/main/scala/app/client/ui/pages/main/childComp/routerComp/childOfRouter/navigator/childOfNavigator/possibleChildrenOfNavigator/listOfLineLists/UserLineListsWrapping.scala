package app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator.listOfLineLists

import app.client.entityCache.entityCacheV1.RootReactCompConstr_Enhancer
import app.client.entityCache.entityCacheV1.types.RootPageConstructorTypes.Depth1CompConstr
import app.client.entityCache.entityCacheV1.types.Vanilla_RootReactComponent_PhantomTypes.UserLineLists_Vanilla_RootReactComp_PhantomType

case class UserLineListsWrapping( wrapper: RootReactCompConstr_Enhancer ) {

//  val wrapperHolder: CacheRoot = new CacheRoot()

  val wrapped_CC: Depth1CompConstr[
    UserLineLists_Vanilla_RootReactComp_PhantomType.type,
    UserLineListsComp.Prop
  ] =
    wrapper
    .depth1CompConstr[
        UserLineLists_Vanilla_RootReactComp_PhantomType.type,
        UserLineListsComp.Prop
      ](
        UserLineListsComp.compConstr
      )

}
