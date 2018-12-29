package experiments.cacheExperiments.cache

import app.shared.data.model.LineText
import app.shared.data.ref.{Ref, RefVal}

//object Cache {
//  var requests: List[Ref[LineText]] = List()
//
//  def askForLineText(r: Ref[LineText] ): Unit = {
//    requests = r :: requests
//  }
//}

case class ReRenderTriggerer(dummyString:String)

object Cache{


  type State=Option[RefVal[LineText]]
  var lineTextOption: State  = None

  var reRenderTriggerer : Option[ReRenderTriggerer]= None


  def read():State = {
    lineTextOption
    // we start an ajax call here if the State is None
    // the ajax call update's the cache and triggers a re-render
    // by calling reRenderTriggerer's callback, which was set in
    // componentDidMount in
    // experiments.cacheExperiments.components.RootComp.compConstructor
  }



}
