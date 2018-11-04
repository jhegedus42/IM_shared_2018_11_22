package app.client.entityCache.entityCacheV1.types

import app.client.ui.pages.main.root_children.materialUI_children.Pages.Page
import app.client.entityCache.entityCacheV1.EntityReaderWriter_State_To_React_Comp
import app.client.entityCache.entityCacheV1.types.Vanilla_RootReactComponent_PhantomTypes.RootReactComponent_MarkerTrait
import japgolly.scalajs.react.extra.router.RouterCtl



case class PropsWithoutEntityReaderWriter[Props](p: Props, ctrl: RouterCtl[Page] )

case class PropsWithInjectedEntityReaderWriter[Props, RootReactComp <: RootReactComponent_MarkerTrait](
    ps:          Props,
    router:      RouterCtl[Page],
    entityCache: EntityReaderWriter_State_To_React_Comp)
