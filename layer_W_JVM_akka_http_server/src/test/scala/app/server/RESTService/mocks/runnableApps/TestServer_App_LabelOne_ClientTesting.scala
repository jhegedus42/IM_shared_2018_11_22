package app.server.RESTService.mocks.runnableApps

import app.server.RESTService.mocks.TestServerFactory
import app.testHelpersServer.state.TestData
import app.testHelpersShared.data.TestDataLabels.LabelOne

object TestServer_App_LabelOne_ClientTesting extends App {
  // d03cb99d90464600b4ec4a6286f2bbb9$4c99b1ca2b825dfc2e311c49f3572327a7c77e8d
  println("before iszunk a medve borere")
  TestServerFactory
  .getTestServer(TestData.getTestDataFromLabels(LabelOne))
  .start(args)
}
