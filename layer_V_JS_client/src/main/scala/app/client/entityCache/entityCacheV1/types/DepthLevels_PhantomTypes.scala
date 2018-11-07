package app.client.entityCache.entityCacheV1.types

object DepthLevels_PhantomTypes {
  sealed trait DepthLevels
  trait Depth1Component extends DepthLevels
  trait Depth2Component extends DepthLevels
}
