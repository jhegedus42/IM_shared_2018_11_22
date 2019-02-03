package experiments.cacheExperiments.cache

import app.shared.data.model.LineText
import app.shared.data.ref.{Ref, RefVal}
import experiments.cacheExperiments.cache.CacheStates.{CacheState, Loading}
import io.circe.generic.auto._
import io.circe.{Decoder, Encoder, Error}
import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.reflect.ClassTag


// indirection , not allowing direct access to EntityCacheMap

object CacheInterface {

  private lazy val cacheLineText: EntityCacheMap[LineText] = new EntityCacheMap[LineText]

  def readLineText(r: Ref[LineText] ): CacheState[LineText] = {
    val res: CacheState[LineText] = cacheLineText.readEntity( r )
    res
  }


}
// ^^^^^^^
// erre lehetni irni type class-okat: vmi altalanos getEntity
// metodust, azaz pl. attol fuggoen h. milyen entity't ker a react comp mas instance hivodik meg...
// de ezt majd irjuk meg azutan ha a konkret dolgok kesz vannak
// cache will have a separate map for each entity
// for each view
// ezek adnak egy type safety-t, nem kell kasztolgatni, az is latszik tisztan, hogy milyen
// entity-t vannak hasznalatban
