package app.client.entityCache.entityCacheV2.visibleToUI.compPropExtender.types.typesBeforeAndAfterPropertyExtension.compProperties

import app.client.entityCache.entityCacheV2.visibleToUI.compPropExtender.types.MarkerObjects_for_Components_Whose_Properties_Can_Be_Extended.A_RootReactComponent
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.PossibleChildOfNavigator
import japgolly.scalajs.react.extra.router.RouterCtl

/**
  *
  * This is a case class into which the properties which will be extended are wrapped.
  *
  * Why is this wrapping needed ?
  *
  * It is needed because it makes sure that the RouterCtl is made available to the
  * component which uses the extended properties + passed properties.
  *
  * Who uses this case class ?
  *
  * Who creates this class ?
  *
  *
  *
  * @param passedProperties  Who creates (writes) these properties ?
  *                           Someone who wants to make some information
  *                           available to the currently mounted root react
  *                           component.
  *
  *                          Who uses (reads) them ?
  *                           The
  * @param ctrl
  * @tparam RootReactComp    Who creates this component ?
  *                          Who uses this component, and for what purpose ?
  *
  * @tparam PassedProperties These are the properties that are passed to the child component
  *                          by its parent component (which is the router).
  */
case class CompPropertiesBeforeExtension[
    RootReactComp <: A_RootReactComponent,
    PassedProperties <: ExtendableProperties_PhantomType[RootReactComp]
  ](passedProperties: PassedProperties,
    ctrl:             RouterCtl[PossibleChildOfNavigator])
