package app.client.ui.pages.usingEntityCacheV1.listOfLineLists

import app.client.entityCache.entityCacheV1.RootReactCompConstr_Enhancer
import app.client.entityCache.entityCacheV1.types.RootPageConstructorTypes.CacheInjectorCompConstructor
import app.client.entityCache.entityCacheV1.types.Vanilla_RootReactComponent_PhantomTypes.UserLineLists_Vanilla_RootReactComp_PhantomType

case class UserLineListsWrapping( wrapper: RootReactCompConstr_Enhancer ) {

//  val wrapperHolder: CacheRoot = new CacheRoot()

  val wrapped_CC: CacheInjectorCompConstructor[
    UserLineLists_Vanilla_RootReactComp_PhantomType.type,
    UserLineListsComp.Prop
  ] =
    wrapper
    .createCacheInjectorCompConstructor[
        UserLineLists_Vanilla_RootReactComp_PhantomType.type,
        UserLineListsComp.Prop
      ](
        UserLineListsComp.compConstr
      )

}
