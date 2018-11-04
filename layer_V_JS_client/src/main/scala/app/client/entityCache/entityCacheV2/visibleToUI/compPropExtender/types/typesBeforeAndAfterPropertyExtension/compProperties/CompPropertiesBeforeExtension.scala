package app.client.entityCache.entityCacheV2.compPropExtender.types.typesBeforeAndAfterPropertyExtension.compProperties

import app.client.entityCache.entityCacheV2.visibleToUI.compPropExtender.types.MarkerObjects_for_Components_Whose_Properties_Can_Be_Extended.A_RootReactComponent
import app.client.ui.pages.main.root_children.materialUI_children.Pages.Page
import japgolly.scalajs.react.extra.router.RouterCtl

/**
  * @param p
  * @param ctrl
  * @tparam RootReactComp This is the component whose properties we are going to extend.
  * @tparam Props
  */
case class CompPropertiesBeforeExtension[
    RootReactComp <: A_RootReactComponent,
    Props <: ExtendableProperties[RootReactComp]
  ](p:    Props,
    ctrl: RouterCtl[Page])
