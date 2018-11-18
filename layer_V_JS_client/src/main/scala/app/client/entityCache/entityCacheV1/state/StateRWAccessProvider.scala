package app.client.entityCache.entityCacheV1.state

/**
  * The supertype of an object that has a var which points to an immutable
  *
  *
  * @tparam DescriptorOfAllowedStatesImpl
  * @tparam StateRWAccesProviderImpl
  */
sealed trait StateRWAccessProvider[
    DescriptorOfAllowedStatesImpl <: DescrpitorOfAllowedStates[DescriptorOfAllowedStatesImpl,
                                                             StateRWAccesProviderImpl],
    StateRWAccesProviderImpl <: StateRWAccessProvider[DescriptorOfAllowedStatesImpl,
                                                      StateRWAccesProviderImpl]] {
  def setState(s: DescriptorOfAllowedStatesImpl )

  def getState: DescriptorOfAllowedStatesImpl
}

case class CurrentlyRoutedPage_StateRWAccessProvider(var page: CurrentlyRoutedPageVarType )
    extends StateRWAccessProvider[CurrentlyRoutedPageVarType, CurrentlyRoutedPage_StateRWAccessProvider] {
  override def setState(s: CurrentlyRoutedPageVarType ): Unit = ???

  override def getState: CurrentlyRoutedPageVarType = ???
}
