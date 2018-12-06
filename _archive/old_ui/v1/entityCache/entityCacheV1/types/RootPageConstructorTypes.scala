package app.client.entityCache.entityCacheV1.types

import app.client.entityCache.entityCacheV1.CacheState
import app.client.entityCache.entityCacheV1.types.componentProperties.{
  D1Comp_Props,
  RouterToD1Props,
  Depth2CompProps_ELI_D1CompProps_With_RouterCtl_With_EntityCache
}
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.URL_STr
import japgolly.scalajs.react.ReactComponentC.ReqProps
import japgolly.scalajs.react.TopNode

object RootPageConstructorTypes {

  /**
    *
    * Constructor providing extended properties to the component which it creates.
    *
    * @tparam URL_TP Type of the data structure that represents the URL
    *                to which this Depth2 Comp Constructor corresponds. Corresponds
    *                here means = the React Component (vdom) created by this Depth2 Comp
    *                Constructor visualises the page which is defined by the URL.
    *
    * @tparam Depth1CompProps_TP Props passed by navigator comp constr to depth 1 comp constructor.
    */
  type Depth2CompConstr_Alias[URL_TP <: URL_STr, Depth1CompProps_TP <: D1Comp_Props] =
    ReqProps[Depth2CompProps_ELI_D1CompProps_With_RouterCtl_With_EntityCache[Depth1CompProps_TP, URL_TP],
             Unit,
             _,
             TopNode]

  /**
    *
    * Keletkezes :
    *
    * Ki hoz ilyen tipust letre ?
    * Illetve, hogyan jon letre ?
    * Mikor jon letre ?
    * Miert jon letre
    * Valamire adott reakcio miatt jon letre. Mi ez a valami ?
    * Mi inditja el a lancot ami ezt letrehozza ?
    *
    *
    * Felhasznalas (olvasas):
    *
    * Ki olvassa ? Navigator Comp Constructor's render method.
    * Kinek kell ?
    * Mihez kell ?
    *
    *
    * @tparam URL_TP the depth2 comp here is the child comp of the depth1 comp which
    *                is created by this depth1 comp constructor
    *
    *
    */
  case class Depth1CompConstrWrapper[URL_TP <: URL_STr, D1_Props <: D1Comp_Props](
      d1Constr: ReqProps[RouterToD1Props[D1_Props], CacheState, _, TopNode])

}
