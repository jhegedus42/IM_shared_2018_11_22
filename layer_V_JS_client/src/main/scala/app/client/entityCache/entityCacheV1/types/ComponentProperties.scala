package app.client.entityCache.entityCacheV1.types

import app.client.ui.pages.main.root_children.materialUI_children.Pages.Page
import app.client.entityCache.entityCacheV1.ImmutableMapHolder
import app.client.entityCache.entityCacheV1.types.Vanilla_RootReactComponent_PhantomTypes.RootReactComponent_MarkerTrait
import japgolly.scalajs.react.extra.router.RouterCtl



case class PropsWithoutEntityReaderWriter[Props](p: Props, ctrl: RouterCtl[Page] )

case class PropsWithInjectedCache[Props, RootReactComp <: RootReactComponent_MarkerTrait](
    ps:          Props,
    router:      RouterCtl[Page],
    entityCache: ImmutableMapHolder)
