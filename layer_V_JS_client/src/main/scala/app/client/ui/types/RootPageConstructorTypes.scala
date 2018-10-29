package app.client.ui.types

import app.client.cache.entityCache.EntityCache
import app.client.ui.types.Vanilla_RootReactComponent_PhantomTypes.Vanilla_RootReactComponent_PhantomType
import japgolly.scalajs.react.ReactComponentC.ReqProps
import japgolly.scalajs.react.TopNode

object RootPageConstructorTypes {

  /**
    * This is a constructor that creates a component which should be wrapped, that is
    * it extends 0378528a_4c99b1ca.
    *
    * @tparam Vanilla_ReactComponent
    * @tparam P
    */
  type VanillaRootPageCompConstr[Vanilla_ReactComponent <: Vanilla_RootReactComponent_PhantomType, P] =
    ReqProps[PropsOfWrappedComp[P, Vanilla_ReactComponent], Unit, _, TopNode]

  /**
    * This is a type that represents a Constructor of a React Component which
    * wraps a 'vanilla' React Component which is the subType of RootReactComponent_PhantomType.
    *
    * @tparam CompName
    * @tparam P
    */
  type WrappedRootPageCompConstr[CompName <: Vanilla_RootReactComponent_PhantomType, P] =
    ReqProps[PropsOfVanillaComp[P], EntityCache, _, TopNode]

}
