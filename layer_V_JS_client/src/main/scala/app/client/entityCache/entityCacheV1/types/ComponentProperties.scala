package app.client.entityCache.entityCacheV1.types

import app.client.ui.pages.main.root_children.materialUI_children.Pages.Page
import app.client.entityCache.entityCacheV1.EntityReaderWriter
import app.client.entityCache.entityCacheV1.types.Vanilla_RootReactComponent_PhantomTypes.RootReactComponent_MarkerTrait
import japgolly.scalajs.react.extra.router.RouterCtl



case class PropsOfVanillaComp[Props](p: Props, ctrl: RouterCtl[Page] )

case class PropsOfWrappedComp[Props, RootReactComp <: RootReactComponent_MarkerTrait](
    ps:          Props,
    router:      RouterCtl[Page],
    entityCache: EntityReaderWriter)
