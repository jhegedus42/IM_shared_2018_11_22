package app.server.RESTService.mocks

object IndexDotHtmlTestTemplate {
  // b66ff55ce83d4555acea49cc2dc5d26a$4c99b1ca2b825dfc2e311c49f3572327a7c77e8d
  import scalatags.Text.all._
  import scalatags.Text.tags2.title

  val jsModulesName          = "layer_V_JS_client"
  val jsModulesNameLowerCase = jsModulesName.map( x => x.toLower )

//  // this is here because we might need it 'one day'
//  def _do_not_use_this_anymore_txt_with_old_comments(testClient: Boolean ) = {
//
//    val testString = if (testClient) "-test-" else "-"
//
//    s"<!DOCTYPE html>" +
//      html(
//        head(
//          title( "Simple example of full scala, full stack single page web app" ),
//          meta( httpEquiv := "Content-Type", content := "text/html; charset=UTF-8" ),
//          //          link(
//          //            rel := "stylesheet", media := "screen" //            href := "./www/assets/stylesheets/react-sortable-hoc-styles.css"
//          //          ),
//          link( rel := "stylesheet", media := "screen", href := "./www/assets/stylesheets/general.css" ),
//          //          link( rel := "stylesheet", media := "screen", href := "./www/assets/stylesheets/ReactCrop.css" )
//          //          link(rel:="stylesheet",media:="screen",href:="./server/target/web/less/main/stylesheets/main.min.css")
//          //  <link rel="stylesheet"    media= "screen"   href=@_asset("stylesheets/react-sortable-hoc-styles.css") >
//        ),
//        body( margin := 0 )(
//          div( id := "rootComp" ),
//          div( id := "rootComp2" ),
//          //          div( id := "jsReactComp" ),
//          //          div( id := "jsReactCrop" ),
//          script( `type` := "text/javascript", src := "./node/generated.js/index-bundle.js" ),
//          script(
//            `type` := "text/javascript",
//            src := s"./${jsModulesName}/target/scala-2" +
//              s".12/$jsModulesNameLowerCase${testString}fastopt.js"
//          ),
//          //          script(
//          //            `type` := "text/javascript",
//          //            src := s"./${jsModulesName}/target/scala-2" +
//          //              s".12/$jsModulesNameLowerCase${testString}jsdeps.js"
//          //          ),
//
//          //          script( "app.client.Main().main()" )
//          script( "demo.app.App().main()" )
//        )
//      )
//  }

  def router_index(testClient: Boolean ) = {
    val testString   = if (testClient) "-test-" else "-"
    val packageName  = "app.client"
    val js_code_path = s"./${jsModulesName}/target/scala-2" + s".12/$jsModulesNameLowerCase${testString}fastopt.js"

    val index_html =
      s"<!DOCTYPE html>" +
        html(
          head(
            title( "Simple example of full scala, full stack single page web app" ),
            meta( httpEquiv := "Content-Type", content := "text/html; charset=UTF-8" ),
            link( rel := "stylesheet", media := "screen", href := "./www/assets/stylesheets/general.css" ),
          ),
          body( margin := 0 )(
            div( id := "rootComp" ),
            script( `type` := "text/javascript", src := "./node/generated.js/index-bundle.js" ),
            script( `type` := "text/javascript", src := js_code_path ),
//              script( s"${packageName}.ReactApp.main()" )
//              script( s"${packageName}.Main().main()" )
            script( "experiments.cache.MainWithCache().main()" ) //, //start scalajs-react app
          )
        )
    index_html
  }

}
