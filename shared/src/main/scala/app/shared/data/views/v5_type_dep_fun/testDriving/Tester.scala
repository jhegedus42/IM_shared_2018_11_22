package app.shared.data.views.v5_type_dep_fun.testDriving

import app.shared.data.views.v5_type_dep_fun.shared.views.View

object Tester extends  App{

  def testGetView[V<:View]() ={

  // we do not consider react in the first test


  // V is the View that will be tested


  // here is what this function will do :

  // 1) creates a HttpServer
  //
  // 2) creates client ajax call interface
  //      it is called ClientSideAjaxInterface
  //      - getView[V<:View](json:JSON,httpEndpoint:HttpEndpoint):JSON
  //          -  this will print the name of the http endpoint
  //               to which this ajax request will be directed
  //
  // 3) creates client side cache


  }


}


