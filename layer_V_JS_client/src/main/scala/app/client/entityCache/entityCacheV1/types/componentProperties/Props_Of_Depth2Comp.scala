package app.client.entityCache.entityCacheV1.types.componentProperties

import app.client.entityCache.entityCacheV1.CacheState
import app.client.entityCache.entityCacheV1.types.Vanilla_RootReactComponent_PhantomTypes.RootComps_PhType
import app.client.ui.pages.main.root_children.materialUI_children.Pages.Page
import japgolly.scalajs.react.extra.router.RouterCtl

/**
  *
  * Who injected the Cache ?
  *
  * [[]]
  *
  * @param ps     value of props passed to Depth 1 (parent) Component.
  * @param router a client API to the router - passed in by the
  *               [[app.client.ui.pages.main.root_children.materialUI_children.RouterComp]] .
  * @param entityCache
  * @tparam D1Comp_Props Type of props passed to Depth 1 Component.
  * @tparam RootComp_PhType The root page which is described
  *                         by the component to which {{{this}}} object is passed as
  *                         property.
  */
case class Props_Of_Depth2Comp[D1Comp_Props, RootComp_PhType <: RootComps_PhType](
    ps:          D1Comp_Props,
    router:      RouterCtl[Page],
    entityCache: CacheState)
