package app.client.entityCache.entityCacheV1.types.componentProperties

import app.client.entityCache.entityCacheV1.CacheState
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.URL_STr
import japgolly.scalajs.react.extra.router.RouterCtl

/**
  *
  * Who injected the Cache ?
  *
  * [[]]
  *
  * @param depth1CompProps value of props passed to Depth 1 (parent) Component.
  * @param router          a client API to the router - passed in by the
  *                        [[app.client.ui.pages.main.childComp.routerComp.MyRouter]] .
  * @tparam D1Comp_Props_TP Type of props passed to Depth 1 Component.
  * @tparam URL_TP The root page which is described
  *                         by the component to which {{{this}}} object is passed as
  *                         property.
  */
case class Depth2CompProps_ELI_D1CompProps_With_RouterCtl_With_EntityCache[
    D1Comp_Props_TP <: D1Comp_Props,
    URL_TP <: URL_STr
  ](depth1CompProps:          D1Comp_Props_TP,
    router:      RouterCtl[URL_STr],
    // BACKLOG change to URL_TP ^^^ and figure out what that change means
    // and why it is needed ? how will it help ? what will it effect ?
    // who will see it ?
    entityCache: CacheState)

trait D1Comp_Props