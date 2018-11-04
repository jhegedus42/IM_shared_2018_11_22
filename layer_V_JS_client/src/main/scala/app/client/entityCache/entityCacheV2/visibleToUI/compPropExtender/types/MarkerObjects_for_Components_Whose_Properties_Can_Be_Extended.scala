package app.client.entityCache.entityCacheV2.visibleToUI.compPropExtender.types

/**
  *
  *
  *
  */

object MarkerObjects_for_Components_Whose_Properties_Can_Be_Extended {

  /**
    *
    * Subtypes of this MarkerObjectTrait have to correspond to React Components that are mounted
    * directly below the root because their child components can get access to
    * the EntityReader if the parent component passes it to them - so there is no need to
    * wrap them. Hence these restrictor types.
    *
    */

  sealed trait A_RootReactComponent // Phantom type

  object LineList_RootComp_PropsExtendable_MarkerObject extends A_RootReactComponent

  object LineDetail_RootComp_PropsExtendable_MarkerObject extends A_RootReactComponent

  object UserLineLists_RootComp_PropsExtendable_MarkerObject extends A_RootReactComponent

}
