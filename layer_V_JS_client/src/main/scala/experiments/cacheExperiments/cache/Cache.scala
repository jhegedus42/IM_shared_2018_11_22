package experiments.cacheExperiments.cache

import app.shared.data.model.LineText
import app.shared.data.ref.Ref

object Cache {
  var requests: List[Ref[LineText]] = List()

  def askForLineText(r: Ref[LineText] ): Unit = {
    requests = r :: requests
  }
}
