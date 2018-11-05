package app.client.entityCache.entityCacheV1.types

/**
  * 0378528a_4c99b1ca
  *
  * Only subclasses of RootReactComponent_PhantomType-s can be
  * Root/Top components in tha React comp. hierarchy because they
  * are the only ones who can be wrapped.
  *
  */

object Vanilla_RootReactComponent_PhantomTypes {

  sealed trait RootComps_PhType // Phantom type

  object LineList_Vanilla_RootReactComp_PhantomType extends RootComps_PhType

  object LineDetail_Vanilla_RootReactComp_PhantomType extends RootComps_PhType

  object UserLineLists_Vanilla_RootReactComp_PhantomType extends RootComps_PhType

}
