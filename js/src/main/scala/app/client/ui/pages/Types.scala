package app.client.ui.pages

import app.client.cache.CacheMap
import app.client.ui.pages.main.root_children.materialUI_children.Pages.Page
import japgolly.scalajs.react.ReactComponentC.ReqProps
import japgolly.scalajs.react.TopNode
import japgolly.scalajs.react.extra.router.RouterCtl

/**
  * Created by joco on 8/22/17.
  */
sealed trait TopPageCompType
// top page is the one which is directly below the router,
// there can be only one routed page at any given moment

object LineListCompType extends TopPageCompType

object LineDetailCompType extends TopPageCompType

object UserLineListsCompType extends TopPageCompType

case class Props2Wrapped[P](p:P, ctrl:RouterCtl[Page])

case class Props2Vanilla[Props,PhantomType<:TopPageCompType](ps:Props, router: RouterCtl[Page], cache:CacheMap)

object Types {
  type Vanilla_CompConstr[CompName<:TopPageCompType, P] = ReqProps[Props2Vanilla[P,CompName], Unit, _, TopNode]

  type Wrapped_CompConstr[CompName<:TopPageCompType, P] = ReqProps[Props2Wrapped[P], CacheMap, _, TopNode]

}