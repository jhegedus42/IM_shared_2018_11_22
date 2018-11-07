package app.client.entityCache.entityCacheV1.types

import app.client.entityCache.entityCacheV1.CacheState
import app.client.entityCache.entityCacheV1.types.componentProperties.{
  PropsGivenByTheRouter_To_Depth1Component,
  Props_Of_Depth2Comp
}
import app.client.ui.pages.main.childComp.routerComp.childOfRouter.navigator.childOfNavigator.MainPage
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
  type Depth2CompConstr[Name_Of_The_Root_Page <: MainPage, Props_Passed_By_The_Parent_Component] =
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
    * @tparam SpacificMainPage_DefinedBy_TheDepth2Comp the depth2 comp here is the child comp of the depth1 comp which
    *                                                  is created by this depth1 comp constructor
    *
    *
    * @tparam Props_Passed_By_Depth1Comp_To_Depth2Comp
    */
  type Depth1CompConstr[
      SpacificMainPage_DefinedBy_TheDepth2Comp <: MainPage,
      Props_Passed_By_Depth1Comp_To_Depth2Comp
  ] =
    ReqProps[PropsGivenByTheRouter_To_Depth1Component[Props_Passed_By_Depth1Comp_To_Depth2Comp], CacheState, _, TopNode]

  // TODO ezt a type aliast ^^^^ lecserelni egy rendes case class-ra, ami becsomagolja az aktualis construktort
  // es akkor meg lehetne kulonboztetni, hogy ki mit csinal
  // depth1 depth2 es hasonlok

}
