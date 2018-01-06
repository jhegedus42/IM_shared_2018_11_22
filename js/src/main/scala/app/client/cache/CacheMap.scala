package app.client.cache

import app.client.cache.wrapper.{ReadRequest,  ReadAndWriteRequestQue, UpdateRequest}
//import app.client.rest.ClientRestAJAX
import app.shared.TypeError
import app.shared.model.Entity.Entity
import app.shared.model.EntityType
import app.shared.model.ref.{Ref, RefDyn, RefVal, RefValDyn}
import io.circe.{Decoder, Encoder}

import scala.concurrent.Future
import scala.reflect.ClassTag
import scala.scalajs.js.|
import scalaz.\/
import app.shared.model.Entity.Entity
import app.shared.model.ref.{Ref, RefDyn}
import slogging.LazyLogging

case class CacheMap(val map: Map[Ref[_<:Entity], CacheVal[_<:Entity]] = Map(),
                    val executor  : ReadAndWriteRequestQue) extends LazyLogging{

  def getEntity[E <: Entity:ClassTag](r: Ref[E]): CacheVal[E] = {

    if (!map.contains(r)) { // csak akkor hivjuk meg ha meg nincs benne a cache-ben ...
      val rr: ReadRequest[E] = ReadRequest(r)
      logger.trace("getEntity - rr:"+rr)
      executor.queReadRequest(rr)
    }

    val res: CacheVal[_<:Entity] = map.getOrElse(r, NotInCache(r))

    res.asInstanceOf[CacheVal[E]]
  }

  def updateEntity[E <: Entity:ClassTag:Decoder:Encoder](rv:RefVal[E]): Unit = {
    // 0 0d33c0acbc0240b9967f48951ddf79ed dispatch write request
    executor.launchUpdateReq(UpdateRequest(rv))
    println("update entity is called" +rv)

    // -- as a response to user events (say pushing button) //    0d33c0acbc0240b9967f48951ddf79ed
  }
}
