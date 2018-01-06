package app.client.rest.commands

import app.client.rest.commands.forTesting.Helpers
import app.shared.rest.TestURLs
import app.testHelpersShared.data.TestDataLabels
import app.testHelpersShared.data.TestDataLabels.TestDataLabel
import org.scalajs.dom.XMLHttpRequest
import org.scalajs.dom.ext.Ajax
import org.scalatest.{Assertion, AsyncFunSuite}

import scala.concurrent.Future



trait BeforeTester {
  self: AsyncFunSuite =>

  def testWithBefore(before: Future[Unit] )(description: String )(testToRun: Future[Assertion] ): Unit = {
    test( description ) { before.flatMap( _ => testToRun ) }
  }

  val resetDBBeforeTest: Future[Unit] =
    Helpers.resetServer( TestDataLabels.LabelOne ).map( x => () )
}

// 1edcc0241f9849b184756247ca8aa02f commit e878a35221f137fbf27443487ae1135ab7d4e199 Fri Oct 27 00:27:05 EEST 2017
