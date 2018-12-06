package app.client.entityCache.entityCacheV2.visibleToUI.compPropExtender.types.typesBeforeAndAfterPropertyExtension.compConstructors



import app.client.entityCache.entityCacheV2.visibleToUI.compPropExtender.types.MarkerObjects_for_Components_Whose_Properties_Can_Be_Extended.A_RootReactComponent
import app.client.entityCache.entityCacheV2.visibleToUI.compPropExtender.types.extendPropertiesWith.EntityReaderWriter
import app.client.entityCache.entityCacheV2.visibleToUI.compPropExtender.types.typesBeforeAndAfterPropertyExtension.compProperties.{CompPropertiesAfterExtension, CompPropertiesBeforeExtension, ExtendableProperties_PhantomType}
import japgolly.scalajs.react.ReactComponentC.ReqProps
import japgolly.scalajs.react.TopNode

/**
  * Types Of ReactComponent Constructors
  * Before And After Extending
  * The Properties That they offer to the Components Which They Create
  *
  */

object TypesAliases_For_ReactCompConstructors {

  /**
    *
    * @tparam Comp This is THE root react component whose properties will be extended.
    * @tparam Props These are the properties of the root react component BEFORE extension.
    */
  type RootReactCompConstrBeforePropertyExtension[
      Comp <: A_RootReactComponent,
      Props <: ExtendableProperties_PhantomType[ Comp ]
  ] =
    ReqProps[CompPropertiesBeforeExtension[Comp, Props],
             Unit,
             _,
             TopNode]

  /**
    *
    * This is the type of a React Component Constructor that constructs a react component
    * whose properties have been ALREADY extended with an EntityReaderWriter.
    *
    * @tparam Props These are the properties of the root react component BEFORE extension.
    * @tparam Comp A root react component whose properties are extended.
    */
  type Constr_AfterExtComp[
      Comp <: A_RootReactComponent,
      Props <: ExtendableProperties_PhantomType[ Comp ]
  ] =
    ReqProps[CompPropertiesAfterExtension[Comp, Props], EntityReaderWriter, _, TopNode]

}
