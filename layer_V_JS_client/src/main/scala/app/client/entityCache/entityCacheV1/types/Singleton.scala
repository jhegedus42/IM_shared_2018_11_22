package app.client.entityCache.entityCacheV1.types

trait Singleton[Type <: Singleton[Type]] {

  lazy val singletonInstance: Type = getInstance()
  def getSingletonInstance:   Type = singletonInstance
  protected def getInstance(): Type
}
