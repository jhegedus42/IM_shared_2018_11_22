package app.client.entityCache.entityCacheV1.types

import app.client.entityCache.entityCacheV1.CacheState
import app.client.entityCache.entityCacheV1.types.componentProperties.{Props4_Depth2CompConstr, Props_Navigator_To_Depth1CompConstr}
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.URL
import japgolly.scalajs.react.ReactComponentC.ReqProps
import japgolly.scalajs.react.TopNode

object RootPageConstructorTypes {

  /**
    *
    * Constructor providing extended properties to the component which it creates.
    *
    * @tparam Descriptor Phantom type corresponding to the root page which is defined by
    *                               the component that is constructed by this constructor.
    *
    * @tparam Props_Passed_By_The_Navigator_To_The_Depth1_Component
    */
  type Depth2CompConstr[Descriptor <: URL,
                        Props_Passed_By_The_Navigator_To_The_Depth1_Component] =
    ReqProps[Props4_Depth2CompConstr[Props_Passed_By_The_Navigator_To_The_Depth1_Component, Descriptor],
             Unit,
             _,
             TopNode]

  /**
    *
    * Ki hoz ilyen tipust letre ?
    *
    * Ki olvassa ?
    *
    *
    * @tparam SpacificMainPage_DefinedBy_TheDepth2Comp the depth2 comp here is the child comp of the depth1 comp which
    *                                                  is created by this depth1 comp constructor
    *
    *
    * @tparam Props_Passed_By_Depth1Comp_To_Depth2Comp
    */
  type Depth1CompConstr[
      SpacificMainPage_DefinedBy_TheDepth2Comp <: URL,
      Props_Passed_By_Depth1Comp_To_Depth2Comp
  ] =
    ReqProps[Props_Navigator_To_Depth1CompConstr[Props_Passed_By_Depth1Comp_To_Depth2Comp], CacheState, _, TopNode]


  case class Depth1CompConstrWrapper[RPD <: URL, Props](
                                                                   wrappedCompConstr: Depth1CompConstr[RPD, Props])
}

