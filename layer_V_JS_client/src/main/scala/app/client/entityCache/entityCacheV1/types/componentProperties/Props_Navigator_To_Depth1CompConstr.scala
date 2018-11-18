package app.client.entityCache.entityCacheV1.types.componentProperties

import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.URL
import japgolly.scalajs.react.extra.router.RouterCtl

/**
  * @param p
  * @param ctrl
  * @tparam WrappedProps The type of the actual properties which are passed to Depth 1 comp.
  */
case class Props_Navigator_To_Depth1CompConstr[WrappedProps](
    p:    WrappedProps,
    ctrl: RouterCtl[URL])
