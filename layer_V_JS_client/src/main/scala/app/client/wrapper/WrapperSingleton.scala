package app.client.wrapper

import app.client.wrapper.wrapperFactory.wrapperFactoryClass.ReactCompWrapperFactory

object WrapperSingleton {

  private lazy val wrapperFactory: ReactCompWrapperFactory = new ReactCompWrapperFactory()
  lazy val wrapper               : ReactCompWrapper        = wrapperFactory.wrapper
}
