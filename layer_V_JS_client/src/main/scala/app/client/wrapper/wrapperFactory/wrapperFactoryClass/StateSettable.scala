package app.client.wrapper.wrapperFactory.wrapperFactoryClass

import app.client.wrapper.EntityReaderWriter

trait StateSettable {
  def setState(c: EntityReaderWriter )
}
