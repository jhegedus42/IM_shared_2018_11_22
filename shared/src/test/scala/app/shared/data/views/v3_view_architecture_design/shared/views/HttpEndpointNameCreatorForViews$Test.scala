package app.shared.data.views.v3_view_architecture_design.shared.views

import app.shared.data.views.v3_view_architecture_design.shared.views.View1_HolderObject.View1
import org.scalatest.FunSuite

/**
  * Created by joco on 03/06/2018.
  */
class HttpEndpointNameCreatorForViews$Test extends FunSuite {

  test("testGetViewHttpEndpointName") {

    val v1: HttpEndpointName = HttpEndpointNameCreatorForViews.getViewHttpEndpointName[View1]()
    assert(v1.name=="getView_View1")

                                      }

}
