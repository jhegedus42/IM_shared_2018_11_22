package app.client.ui.pages

import app.client.cache.entityCache.EntityCacheMap
import app.client.ui.pages.main.root_children.materialUI_children.Pages.Page
import japgolly.scalajs.react.ReactComponentC.ReqProps
import japgolly.scalajs.react.TopNode
import japgolly.scalajs.react.extra.router.RouterCtl

/**
  * 0378528a_4c99b1ca
  *
  * Only subclasses of RootReactComponent_PhantomType-s can be
  * Root/Top components in tha React comp. hierarchy because they
  * are the only ones who can be wrapped.
  *
  */
sealed trait Wrappable_RootReactComponent // Phantom type

object LineList_Wrappable_RootReactComp_PhantomType extends Wrappable_RootReactComponent

object LineDetail_Wrappable_RootReactComp_PhantomType extends Wrappable_RootReactComponent

object UserLineLists_Wrappable_RootReactComp_PhantomType extends Wrappable_RootReactComponent

case class PropsOfInnerComp[P](p: P, ctrl: RouterCtl[Page] )

case class PropsOfOuterComp[Props, PhantomType <: Wrappable_RootReactComponent](
    ps:          Props,
    router:      RouterCtl[Page],
    entityCache: EntityCacheMap)

object Types {

  /**
    * This is a constructor that creates a component which can be wrapped, that is
    * it extends 0378528a_4c99b1ca.
    *
    * @tparam RootComponent_NotYetWrapped
    * @tparam P
    */
  type NotYetWrappedCompConstr[RootComponent_NotYetWrapped <: Wrappable_RootReactComponent, P] =
    ReqProps[PropsOfOuterComp[P, RootComponent_NotYetWrapped], Unit, _, TopNode]

  /**
    * This is a type that represents a Constructor of a React Component which
    * wraps a 'vanilla' React Component which is the subType of RootReactComponent_PhantomType.
    *
    * @tparam CompName
    * @tparam P
    */
  type WrappedCompConstr[CompName <: Wrappable_RootReactComponent, P] =
    ReqProps[PropsOfInnerComp[P], EntityCacheMap, _, TopNode]

}
