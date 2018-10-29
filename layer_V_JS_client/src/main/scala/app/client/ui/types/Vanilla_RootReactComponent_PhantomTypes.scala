package app.client.ui.types

/**
  * 0378528a_4c99b1ca
  *
  * Only subclasses of RootReactComponent_PhantomType-s can be
  * Root/Top components in tha React comp. hierarchy because they
  * are the only ones who can be wrapped.
  *
  */

object Vanilla_RootReactComponent_PhantomTypes {

  sealed trait Vanilla_RootReactComponent_PhantomType // Phantom type

  object LineList_Vanilla_RootReactComp_PhantomType extends Vanilla_RootReactComponent_PhantomType

  object LineDetail_Vanilla_RootReactComp_PhantomType extends Vanilla_RootReactComponent_PhantomType

  object UserLineLists_Vanilla_RootReactComp_PhantomType extends Vanilla_RootReactComponent_PhantomType

}
