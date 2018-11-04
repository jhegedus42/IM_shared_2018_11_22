package app.client.entityCache.entityCacheV1.types

import app.client.entityCache.entityCacheV1.EntityReaderWriter_State_To_React_Comp
import app.client.entityCache.entityCacheV1.types.Vanilla_RootReactComponent_PhantomTypes.RootReactComponent_MarkerTrait
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
  type Constructor_Providing_ExtendedProperties[Name_Of_The_Root_Page <: RootReactComponent_MarkerTrait,
                                                Props_Passed_By_The_Parent_Component] =
    ReqProps[PropsWithInjectedEntityReaderWriter[Props_Passed_By_The_Parent_Component, Name_Of_The_Root_Page],
             Unit,
             _,
             TopNode]

  /**
    *
    * @tparam Name_Of_The_Root_Page
    * @tparam Props_Passed_By_TheParentComponent
    */
  type Constructor_Used_By_The_Parent_Component[
      Name_Of_The_Root_Page <: RootReactComponent_MarkerTrait,
      Props_Passed_By_TheParentComponent
  ] =
    ReqProps[PropsWithoutEntityReaderWriter[Props_Passed_By_TheParentComponent], EntityReaderWriter_State_To_React_Comp, _, TopNode]



}
