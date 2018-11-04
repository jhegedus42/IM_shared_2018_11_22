package app.client.entityCache.entityCacheV1.types

import app.client.entityCache.entityCacheV1.EntityReaderWriter
import app.client.entityCache.entityCacheV1.types.Vanilla_RootReactComponent_PhantomTypes.RootReactComponent_MarkerTrait
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
  type VanillaRootPageCompConstr[Vanilla_ReactComponent <: RootReactComponent_MarkerTrait, P] =
    ReqProps[PropsOfWrappedComp[P, Vanilla_ReactComponent], Unit, _, TopNode]

  /**
    * This is a type that represents a Constructor of a React Component which
    * wraps a 'vanilla' React Component which is the subType of RootReactComponent_PhantomType.
    *
    * @tparam CompName
    * @tparam P
    */
  type WrappedRootPageCompConstr[CompName <: RootReactComponent_MarkerTrait, P] =
    ReqProps[PropsOfVanillaComp[P], EntityReaderWriter, _, TopNode]

}
