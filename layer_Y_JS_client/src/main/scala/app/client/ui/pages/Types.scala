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
  *
  */

sealed trait RootReactComponent_PhantomType // Phantom type

object LineList_RootReactCompType extends RootReactComponent_PhantomType

object LineDetail_RootReactCompType$ extends RootReactComponent_PhantomType

object UserLineLists_RootReactCompType extends RootReactComponent_PhantomType



case class PropsOfInnerComp[P](p: P, ctrl: RouterCtl[Page] )

case class PropsOfOuterComp[Props, PhantomType <: RootReactComponent_PhantomType](
    ps:          Props,
    router:      RouterCtl[Page],
    entityCache: EntityCacheMap)



object Types {

  /**
    * This is a constructor that creates a component which can be wrapped, that is
    * it extends 0378528a_4c99b1ca.
    *
    * @tparam TypeOfTheComponentWhichWillBeWrapped
    * @tparam P
    */
  type InnerCompConstr[TypeOfTheComponentWhichWillBeWrapped <: RootReactComponent_PhantomType, P] =
    ReqProps[PropsOfOuterComp[P, TypeOfTheComponentWhichWillBeWrapped], Unit, _, TopNode]

  /**
    * This is a type that represents a Constructor of a React Component which
    * wraps a 'vanilla' React Component which is the subType of RootReactComponent_PhantomType.
    *
    * @tparam CompName
    * @tparam P
    */
  type OuterCompConstr[CompName <: RootReactComponent_PhantomType, P] =
    ReqProps[PropsOfInnerComp[P], EntityCacheMap, _, TopNode]

}
