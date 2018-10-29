package app.client.wrapper.types

import app.client.ui.pages.main.root_children.materialUI_children.Pages.Page
import app.client.wrapper.EntityCache
import app.client.wrapper.types.Vanilla_RootReactComponent_PhantomTypes.Vanilla_RootReactComponent_PhantomType
import japgolly.scalajs.react.extra.router.RouterCtl



case class PropsOfVanillaComp[Props](p: Props, ctrl: RouterCtl[Page] )

case class PropsOfWrappedComp[Props, RootReactComp <: Vanilla_RootReactComponent_PhantomType](
    ps:          Props,
    router:      RouterCtl[Page],
    entityCache: EntityCache)
