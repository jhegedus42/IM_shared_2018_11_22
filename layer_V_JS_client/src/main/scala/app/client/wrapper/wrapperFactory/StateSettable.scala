package app.client.wrapper.wrapperFactory

import app.client.wrapper.EntityReaderWriter

trait StateSettable {
  def setState(c: EntityReaderWriter )
}
