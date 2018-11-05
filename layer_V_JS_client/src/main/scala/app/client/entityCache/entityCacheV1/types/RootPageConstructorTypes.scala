package app.client.entityCache.entityCacheV1.types

import app.client.entityCache.entityCacheV1.CacheState
import app.client.entityCache.entityCacheV1.types.Vanilla_RootReactComponent_PhantomTypes.RootComps_PhType
import app.client.entityCache.entityCacheV1.types.componentProperties.{Props_Of_Depth2Comp, PropsGivenByTheRouter_To_Depth1Component}
import japgolly.scalajs.react.ReactComponentC.ReqProps
import japgolly.scalajs.react.TopNode

object RootPageConstructorTypes {

  /**
    *
    * Constructor providing extended properties to the component which it creates.
    *
    * @tparam Name_Of_The_Root_Page Phantom type corresponding to the root page which is defined by
    *                               the component that is constructed by this constructor.
    *
    * @tparam Props_Passed_By_The_Parent_Component
    */
  type Depth2CompConstr[Name_Of_The_Root_Page <: RootComps_PhType,
                               Props_Passed_By_The_Parent_Component] =
    ReqProps[Props_Of_Depth2Comp[Props_Passed_By_The_Parent_Component, Name_Of_The_Root_Page],
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
    * @tparam Name_Of_The_Root_Page
    * @tparam Props_Passed_By_TheParentComponent
    */
  type Depth1CompConstr[
      Name_Of_The_Root_Page <: RootComps_PhType,
      Props_Passed_By_TheParentComponent
  ] =
    ReqProps[PropsGivenByTheRouter_To_Depth1Component[Props_Passed_By_TheParentComponent], CacheState, _, TopNode]

}
