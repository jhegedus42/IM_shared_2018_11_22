package app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator_depth1Components.listOfLineLists

import app.client.entityCache.entityCacheV1.RootReactCompConstr_Enhancer
import app.client.entityCache.entityCacheV1.types.RootPageConstructorTypes.{Depth1CompConstrWrapper, Depth1CompConstr_Alias}
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.LineListsOfUser_URL

case class UserLineListsWrapping(wrapper: RootReactCompConstr_Enhancer ) {

//  val wrapperHolder: CacheRoot = new CacheRoot()

  val wrapped_CC:
    Depth1CompConstrWrapper[LineListsOfUser_URL ,
  UserLineListsCompConstr_Holder.D1CompProps_UserLineList ]
  =
    wrapper
      .create_Depth1CompConstr_by_wrapping_Depth2CompConstructor[
        LineListsOfUser_URL,
        UserLineListsCompConstr_Holder.D1CompProps_UserLineList
      ](
         UserLineListsCompConstr_Holder.compConstr
      )

}
