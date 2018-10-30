package app.client.wrapper.wrapperFactory.wrapperFactoryClass

import app.client.wrapper.EntityReaderWriter

private [wrapperFactoryClass] trait EntityReaderWriterFactory {

  def createNewEntityReaderWriter: EntityReaderWriter
}
