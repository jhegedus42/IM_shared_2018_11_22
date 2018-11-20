package app.client.entityCache.entityCacheV1.types.componentProperties

import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.URL_STr
import japgolly.scalajs.react.extra.router.RouterCtl

/**
  *
  *
  * @param p
  * @param ctrl
  * @tparam Depth1CompProps The type of the actual properties which are passed to Depth 1 comp by the Router.
  */
case class Depth1CompProps_With_RouterCtl[Depth1CompProps<:D1Comp_Props](
    p:    Depth1CompProps,
    ctrl: RouterCtl[URL_STr])

