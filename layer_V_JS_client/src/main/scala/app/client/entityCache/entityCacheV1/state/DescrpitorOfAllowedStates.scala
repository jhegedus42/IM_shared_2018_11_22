package app.client.entityCache.entityCacheV1.state

/**
  * Contract : subtypes of this trait can only allow (contain, in the sense that a type is a set that
  * contains the possible values "belonging" to that type) values that are immutable objects.
  *
  * In other words, subtypes of this trait (classes) describe the possible states (allowed states) for a given
  * subtype of [[app.client.entityCache.entityCacheV1.state.StateRWAccessProvider]].
  *
  * @tparam DescriptorOfAllowedStatesImpl
  * @tparam StateRWAccessProviderImpl
  */
sealed trait DescrpitorOfAllowedStates[
    DescriptorOfAllowedStatesImpl <: DescrpitorOfAllowedStates[DescriptorOfAllowedStatesImpl, StateRWAccessProviderImpl],
    StateRWAccessProviderImpl <: StateRWAccessProvider[DescriptorOfAllowedStatesImpl, StateRWAccessProviderImpl]]

case class CurrentlyRoutedPageVarType(str: String )
    extends DescrpitorOfAllowedStates[CurrentlyRoutedPageVarType, CurrentlyRoutedPage_StateRWAccessProvider]

// allowed cache states