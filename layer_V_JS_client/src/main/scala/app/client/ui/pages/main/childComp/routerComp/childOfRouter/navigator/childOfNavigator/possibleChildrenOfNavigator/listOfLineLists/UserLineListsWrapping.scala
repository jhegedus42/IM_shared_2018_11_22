package app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator.listOfLineLists

import app.client.entityCache.entityCacheV1.RootReactCompConstr_Enhancer
import app.client.entityCache.entityCacheV1.types.RootPageConstructorTypes.Depth1CompConstr

case class UserLineListsWrapping( wrapper: RootReactCompConstr_Enhancer ) {

//  val wrapperHolder: CacheRoot = new CacheRoot()

  val wrapped_CC: Depth1CompConstr[
    ListOfLineListsOfAGivenUser_Page,
    UserLineListsComp.Prop
  ] =
    wrapper
    .depth1CompConstr[
      ListOfLineListsOfAGivenUser_Page,
        UserLineListsComp.Prop
      ](
        UserLineListsComp.compConstr
      )

}
