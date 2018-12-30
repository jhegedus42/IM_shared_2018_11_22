package experiments.cacheExperiments.cache

import app.shared.SomeError_Trait
import app.shared.data.model.Entity.Entity
import app.shared.data.model.LineText
import app.shared.data.ref.{Ref, RefVal}
import app.testHelpersShared.data.TestEntities
import experiments.cacheExperiments.cache.CacheStates.{CacheState, Loading}
import scalaz.\/

import scala.concurrent.{ExecutionContextExecutor, Future}

//object Cache {
//  var requests: List[Ref[LineText]] = List()
//
//  def askForLineText(r: Ref[LineText] ): Unit = {
//    requests = r :: requests
//  }
//}

case class ReRenderTriggerer(triggerReRender: Unit => Unit )

object AJAXApi {}

object CacheStates {
  sealed trait CacheState[E]
  case class Loading[E](r: Ref[E] ) extends CacheState[E]
  case class Loaded[E](r:  Ref[E], refVal: RefVal[E] ) extends CacheState[E]
}

class EntityCacheMap[E <: Entity]() {
  var map: Map[Ref[E], CacheState[E]] = Map()

  def readEntity(refToEntity: Ref[E] ): CacheState[E] = {
    if (!map.contains( refToEntity )) {
      val loading = Loading( refToEntity )
      pullEntityFromServerIntoCache()
    } else map.get( refToEntity )



    def pullEntityFromServerIntoCache(ref: Ref[E] ) = {
      implicit def executionContext: ExecutionContextExecutor =
        scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
      import app.client.rest.commands.generalCRUD.GetEntityAJAX.getEntity
      import io.circe.generic.auto._
      val ref: Ref[LineText] = Ref.makeWithUUID[LineText]( TestEntities.refValOfLineV0.r.uuid )
      println( "elkuldunk a kovetkezo sorban egy ajax-ot" )
      val res: Future[Unit] = getEntity[LineText]( ref ).map(
        x => {
          println( s"az entity visszavage az AJAXApi-ban $x" )
          val lt:  \/[SomeError_Trait, RefVal[LineText]] = x
          val res: Option[RefVal[LineText]]              = lt.toOption

          println( "we will update the cache here" )
          Cache.map = res // MURDEEEEERRRR BLOOOODDD HEELLLLLLL DAAAAANNNNNGGGGEEEEERRRR :)
          println( "we will call here the re render trigger" )
          val r = Cache.reRenderTriggerer.get // WE GONNA BURN IN HELLL FOR THIS UNSAFE GET !!!!
          r.triggerReRender()
          // here we trigger a re-render
        }
      )
    }

  }

}

object Cache {

  //cache will have a separate map for each entity, each view

  var reRenderTriggerer: Option[ReRenderTriggerer] = None

}
