package app.client.entityCache.entityCacheV1.types.componentProperties

import app.client.entityCache.entityCacheV1.CacheState
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.URL
import japgolly.scalajs.react.extra.router.RouterCtl

/**
  *
  * Who injected the Cache ?
  *
  * [[]]
  *
  * @param ps     value of props passed to Depth 1 (parent) Component.
  * @param router a client API to the router - passed in by the
  *               [[app.client.ui.pages.main.childComp.routerComp.MyRouter]] .
  * @tparam D1Comp_Props Type of props passed to Depth 1 Component.
  * @tparam Descriptor The root page which is described
  *                         by the component to which {{{this}}} object is passed as
  *                         property.
  */

case class Props4_Depth2CompConstr[
    D1Comp_Props,
    Descriptor <: URL
  ](ps:          D1Comp_Props,
    router:      RouterCtl[URL],
    entityCache: CacheState)
