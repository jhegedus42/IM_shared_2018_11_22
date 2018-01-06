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

object LineList extends TopPageCompType

object LineDetail extends TopPageCompType

object UserLineListsType extends TopPageCompType

object Types {
  type CompConstr[CompName<:TopPageCompType, P] = ReqProps[PropsHolder[P,CompName], Unit, _, TopNode]

  type Wrapped_CompConstr[CompName<:TopPageCompType, P] = ReqProps[(P, RouterCtl[Page]), CacheMap, _, TopNode]

  case class PropsHolder[Props,PhantomType<:TopPageCompType](ps:Props,
                                                             router : RouterCtl[Page], cache:CacheMap)
}