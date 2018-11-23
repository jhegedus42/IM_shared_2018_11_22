package app.client.entityCache.entityCacheV1.types.componentProperties

import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.URL_STr
import japgolly.scalajs.react.extra.router.RouterCtl

/**
  *
  *
  * @param p
  * @param ctrl
  * @tparam D1Props The type of the actual properties which are passed to Depth 1 comp by the Router.
  */
case class RouterToD1Props[D1Props <: D1Comp_Props](p: D1Props, ctrl: RouterCtl[URL_STr] )
