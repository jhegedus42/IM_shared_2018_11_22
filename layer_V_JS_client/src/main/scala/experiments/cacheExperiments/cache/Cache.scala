package experiments.cacheExperiments.cache

import app.shared.SomeError_Trait
import app.shared.data.model.LineText
import app.shared.data.ref.{Ref, RefVal}
import app.testHelpersShared.data.TestEntities
import scalaz.\/

import scala.concurrent.{ExecutionContextExecutor, Future}

//object Cache {
//  var requests: List[Ref[LineText]] = List()
//
//  def askForLineText(r: Ref[LineText] ): Unit = {
//    requests = r :: requests
//  }
//}

case class ReRenderTriggerer(triggerer: Unit => Unit )

object AJAXApi {

  def fetchDataFromServer() =
     {
      implicit def executionContext: ExecutionContextExecutor =
        scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
      import app.client.rest.commands.generalCRUD.GetEntityAJAX.getEntity
      import io.circe.generic.auto._
      val ref: Ref[LineText] = Ref.makeWithUUID[LineText]( TestEntities.refValOfLineV0.r.uuid )
      val res: Future[Unit] = getEntity[LineText]( ref ).map(
        x => {
          println( s"az entity visszavage az AJAXApi-ban $x" )
          val lt:  \/[SomeError_Trait, RefVal[LineText]] = x
          val res: Option[RefVal[LineText]]              = lt.toOption

          println("we will update the cache here")
          Cache.lineTextOption =res  // MURDEEEEERRRR BLOOOODDD HEELLLLLLL DAAAAANNNNNGGGGEEEEERRRR :)
          println("we will call here the re render trigger")
          TODO TODO TODO ^^^^^^^^
        }
      )
    }
}

object Cache {

  type State = Option[RefVal[LineText]]
  var lineTextOption: State = None

  var reRenderTriggerer: Option[ReRenderTriggerer] = None

  def read(): State = {
    if ( lineTextOption.isEmpty ) {
      AJAXApi.fetchDataFromServer()
      None
    } else lineTextOption

    // we call reRenderTriggerer's callback, which was set in
    // componentDidMount in
    // experiments.cacheExperiments.components.RootComp.compConstructor
  }

}
