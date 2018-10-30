package app.client.wrapper

import app.client.wrapper.wrapperFactory.{ReadWriteRequestHandler, UpdateRequest}
//import app.client.rest.ClientRestAJAX
import app.shared.data.model.Entity.Entity
import app.shared.data.ref.{Ref, RefVal}
import io.circe.{Decoder, Encoder}
import slogging.LazyLogging
import scala.reflect.ClassTag

/**
  * Ki hozza ezt létre?
  *
  * Mikor hozzák ezt létre ?
  *
  * Ki használja ezt ?
  *
  * @param map
  * @param entityReadWriteRequestHandler
  */
case class EntityReaderWriter(
    map:                           Map[Ref[_ <: Entity], EntityCacheVal[_ <: Entity]] = Map(),
    entityReadWriteRequestHandler: ReadWriteRequestHandler)
    extends LazyLogging {

  def getEntity[E <: Entity: ClassTag](r: Ref[E] ): EntityCacheVal[E] = {

    if (!map.contains( r )) { // csak akkor hivjuk meg ha meg nincs benne a cache-ben ...
      val readRequest: ReadRequest[E] = ReadRequest( r )
      logger.trace( "getEntity - readRequest:" + readRequest )
      entityReadWriteRequestHandler.handleReadRequest( readRequest )
    }

    val res: EntityCacheVal[_ <: Entity] = map.getOrElse( r, NotInCache( r ) )

    res.asInstanceOf[EntityCacheVal[E]]
  }

  def updateEntity[E <: Entity: ClassTag: Decoder: Encoder](rv: RefVal[E] ): Unit = {
    // 0 0d33c0acbc0240b9967f48951ddf79ed dispatch write request
    entityReadWriteRequestHandler.handleUpdateReq( UpdateRequest( rv ) )
    println( "update entity is called" + rv )

    // -- as a response to user events (say pushing button) //    0d33c0acbc0240b9967f48951ddf79ed
  }

}
