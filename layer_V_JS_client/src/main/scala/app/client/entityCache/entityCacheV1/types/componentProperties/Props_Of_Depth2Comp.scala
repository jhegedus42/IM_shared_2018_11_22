package app.client.entityCache.entityCacheV1.types.componentProperties

import app.client.entityCache.entityCacheV1.CacheState
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.PossibleChildOfNavigator
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
  * @param entityCache
  * @tparam D1Comp_Props Type of props passed to Depth 1 Component.
  * @tparam RootComp_PhType The root page which is described
  *                         by the component to which {{{this}}} object is passed as
  *                         property.
  */
case class Props_Of_Depth2Comp[D1Comp_Props, RootComp_PhType <: PossibleChildOfNavigator](
    ps:          D1Comp_Props,
    router:      RouterCtl[PossibleChildOfNavigator],
    entityCache: CacheState)
