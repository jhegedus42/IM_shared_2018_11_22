package app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator_depth1Components.listOfLineLists

import app.client.entityCache.entityCacheV1.RootReactCompConstr_Enhancer
import app.client.entityCache.entityCacheV1.types.RootPageConstructorTypes.Depth1CompConstr
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.LineListsOfUser

case class UserLineListsWrapping( wrapper: RootReactCompConstr_Enhancer ) {

//  val wrapperHolder: CacheRoot = new CacheRoot()

  val wrapped_CC: Depth1CompConstr[
    LineListsOfUser,
    UserLineListsComp.Prop
  ] =
    wrapper
    .create_Depth1CompConstr_by_wrapping_Depth2CompConstructor[
      LineListsOfUser,
        UserLineListsComp.Prop
      ](
        UserLineListsComp.compConstr
      )

}
