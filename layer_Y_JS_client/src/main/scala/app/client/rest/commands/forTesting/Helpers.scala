package app.client.rest.commands.forTesting

import app.testHelpersShared.data.TestDataLabels
import app.testHelpersShared.data.TestDataLabels.TestDataLabel
import org.scalajs.dom.XMLHttpRequest
import org.scalajs.dom.ext.Ajax

import scala.concurrent.Future

object Helpers {

  def resetServer(label: TestDataLabel ): Future[XMLHttpRequest] = {
//    import io.circe.generic.auto._
//    import io.circe.syntax._

    val json: String = TestDataLabels.toJSON(label).spaces2
//    val json="bla"
    println( "put test payload:" + json )
    val headers = Map( "Content-Type" -> "application/json" )
//    val url: String = ResetURL().clientEndpointWithHost.asString

    val url: String = "/resetState"

    println( s"resetServer + url:${url}" )
    val f: Future[XMLHttpRequest] =
      Ajax.post( url, json, headers = headers )
    f
  }

  def resetServerToLabelOne()=resetServer(TestDataLabels.LabelOne)
  def resetServerToLabelThree()=resetServer(TestDataLabels.LabelThree)
}
