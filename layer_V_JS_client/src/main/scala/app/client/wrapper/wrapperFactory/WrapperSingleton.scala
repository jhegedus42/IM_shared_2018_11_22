package app.client.wrapper.wrapperFactory

import app.client.wrapper.wrapperFactory.wrapperFactoryClass.WrapperFactory

object WrapperSingleton {

  private lazy val wrapperFactory: WrapperFactory = new WrapperFactory()
  lazy val wrapper = wrapperFactory.wrapper
}
