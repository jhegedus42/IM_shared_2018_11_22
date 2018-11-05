package app.client.entityCache.entityCacheV1.types

import app.client.entityCache.entityCacheV1.CacheState
import app.client.entityCache.entityCacheV1.types.componentProperties.{PropsGivenByTheRouter_To_Depth1Component, Props_Of_Depth2Comp}
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.PossibleChildOfNavigator
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
  type Depth2CompConstr[Name_Of_The_Root_Page <: PossibleChildOfNavigator,
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
      Name_Of_The_Root_Page <: PossibleChildOfNavigator,
      Props_Passed_By_TheParentComponent
  ] =
    ReqProps[PropsGivenByTheRouter_To_Depth1Component[Props_Passed_By_TheParentComponent], CacheState, _, TopNode]

}
