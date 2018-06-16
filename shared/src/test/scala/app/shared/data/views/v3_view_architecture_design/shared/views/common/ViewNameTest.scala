package app.shared.data.views.v3_view_architecture_design.shared.views.common

import app.shared.data.views.v3_view_architecture_design.shared.views.View1_HolderObject.View1
import org.scalatest.FunSuite

/**
  * Created by joco on 03/06/2018.
  */
class ViewNameTest extends FunSuite {

  test("testGetViewName")
  {
      val viewName= ViewName.getViewName[View1]()
      println(viewName)
      assert(viewName.shortName=="View1")
      println("assertion success View1")

  }

}
