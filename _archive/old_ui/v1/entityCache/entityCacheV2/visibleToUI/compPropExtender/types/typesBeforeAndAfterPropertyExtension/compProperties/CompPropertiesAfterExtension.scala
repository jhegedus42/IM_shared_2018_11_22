//package app.client.entityCache.entityCacheV2.compPropExtender.types.typesBeforeAndAfterPropertyExtension.compProperties
package app.client.entityCache.entityCacheV2.visibleToUI.compPropExtender.types.typesBeforeAndAfterPropertyExtension.compProperties

import app.client.entityCache.entityCacheV2.visibleToUI.compPropExtender.types.MarkerObjects_for_Components_Whose_Properties_Can_Be_Extended.A_RootReactComponent
import app.client.entityCache.entityCacheV2.visibleToUI.compPropExtender.types.extendPropertiesWith.EntityReaderWriter
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.URL_STr
import japgolly.scalajs.react.extra.router.RouterCtl

case class CompPropertiesAfterExtension[
    RootReactComp <: A_RootReactComponent,
    Props <: ExtendableProperties_PhantomType[RootReactComp]
  ](ps:          Props,
    router:      RouterCtl[URL_STr],
    entityCache: EntityReaderWriter)
