package app.client.entityCache.entityCacheV1.types.componentProperties

import app.client.entityCache.entityCacheV1.CacheState
import app.client.entityCache.entityCacheV1.types.Vanilla_RootReactComponent_PhantomTypes.RootReactComponent_MarkerTrait
import app.client.ui.pages.main.root_children.materialUI_children.Pages.Page
import japgolly.scalajs.react.extra.router.RouterCtl

case class PropsWithInjectedCache_Fed_To_Depth2Comp[Props, RootReactComp <: RootReactComponent_MarkerTrait](
    ps:          Props,
    router:      RouterCtl[Page],
    entityCache: CacheState)
