package experiments.cacheExperiments.cache

import app.shared.SomeError_Trait
import app.shared.data.model.Entity.Entity
import app.shared.data.model.LineText
import app.shared.data.ref.uuid.UUID
import app.shared.data.ref.{Ref, RefVal}
import app.testHelpersShared.data.TestEntities
import experiments.cacheExperiments.cache.ajax.AJAXApi.{Completed__ReadEntity_AjaxCall, InFlight_ReadEntity}
import experiments.cacheExperiments.cache.CacheStates.{CacheState, Loading}
import scalaz.\/
import app.client.rest.commands.generalCRUD.GetEntityAJAX.getEntity
import io.circe.generic.auto._
import io.circe.generic.auto._
import io.circe.{Decoder, Encoder, Error}

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.reflect.ClassTag











object CacheFaszad {

  private lazy val cacheLineText: EntityCacheMap[LineText] = new EntityCacheMap[LineText]

  def readLineText(r: Ref[LineText] ): CacheState[LineText] = {
    val res: CacheState[LineText] = cacheLineText.readEntity( r )
    res
  }
  // erre lehetni irni type class-okat: vmi altalanos getEntity
  // metodust, azaz pl. attol fuggoen h. milyen entity't ker a react comp mas instance hivodik meg...
  // de ezt majd irjuk meg azutan ha a konkret dolgok kesz vannak
  // cache will have a separate map for each entity
  // for each view
  // ezek adnak egy type safety-t, nem kell kasztolgatni, az is latszik tisztan, hogy milyen
  // entity-t vannak hasznalatban

}
