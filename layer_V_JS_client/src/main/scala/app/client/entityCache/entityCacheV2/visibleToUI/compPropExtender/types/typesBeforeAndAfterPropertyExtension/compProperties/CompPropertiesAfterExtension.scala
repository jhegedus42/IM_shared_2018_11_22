package app.client.entityCache.entityCacheV2.compPropExtender.types.typesBeforeAndAfterPropertyExtension.compProperties

import app.client.entityCache.entityCacheV2.visibleToUI.compPropExtender.types.MarkerObjects_for_Components_Whose_Properties_Can_Be_Extended.A_RootReactComponent
import app.client.entityCache.entityCacheV2.visibleToUI.compPropExtender.types.extendPropertiesWith.EntityReaderWriter
import app.client.ui.pages.main.root_children.materialUI_children.Pages.Page
import japgolly.scalajs.react.extra.router.RouterCtl

case class CompPropertiesAfterExtension[
    RootReactComp <: A_RootReactComponent,
    Props <: ExtendableProperties[RootReactComp]
  ](ps:          Props,
    router:      RouterCtl[Page],
    entityCache: EntityReaderWriter)
