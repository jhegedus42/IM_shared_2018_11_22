package app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.possibleChildrenOfNavigator_depth1Components.listOfLineLists

import app.client.entityCache.entityCacheV1.D2toD1Transformer
import app.client.entityCache.entityCacheV1.types.RootPageConstructorTypes.{
  Depth1CompConstrWrapper,
  Depth2CompConstr_Alias
}
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.LineListsOfUser_URL

case class UserLineListsWrapping(wrapper: D2toD1Transformer ) {

//  val wrapperHolder: CacheRoot = new CacheRoot()

  val wrapped_CC: Depth1CompConstrWrapper[LineListsOfUser_URL,
                                          UserLineListsCompConstr_Holder.D1CompProps_UserLineList] = {

    val f
      : Depth2CompConstr_Alias[LineListsOfUser_URL, UserLineListsCompConstr_Holder.D1CompProps_UserLineList] =
      UserLineListsCompConstr_Holder.compConstr

    val res: Depth1CompConstrWrapper[LineListsOfUser_URL,
                                     UserLineListsCompConstr_Holder.D1CompProps_UserLineList] = wrapper
      .trD2toD1[LineListsOfUser_URL, UserLineListsCompConstr_Holder.D1CompProps_UserLineList]( f )
    res
  }

}
