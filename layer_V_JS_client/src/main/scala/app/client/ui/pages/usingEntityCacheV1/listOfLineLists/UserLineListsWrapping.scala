package app.client.ui.pages.usingEntityCacheV1.listOfLineLists

import app.client.entityCache.entityCacheV1.RootReactCompConstr_Enhancer
import app.client.entityCache.entityCacheV1.types.RootPageConstructorTypes.Constructor_Used_By_The_Parent_Component
import app.client.entityCache.entityCacheV1.types.Vanilla_RootReactComponent_PhantomTypes.UserLineLists_Vanilla_RootReactComp_PhantomType

case class UserLineListsWrapping( wrapper: RootReactCompConstr_Enhancer ) {

//  val wrapperHolder: CacheRoot = new CacheRoot()

  val wrapped_CC: Constructor_Used_By_The_Parent_Component[
    UserLineLists_Vanilla_RootReactComp_PhantomType.type,
    UserLineListsComp.Prop
  ] =
    wrapper
    .create_Enhanced_RootReactComp_Constructor[
        UserLineLists_Vanilla_RootReactComp_PhantomType.type,
        UserLineListsComp.Prop
      ](
        UserLineListsComp.compConstr
      )

}
