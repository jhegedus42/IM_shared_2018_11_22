package app.server.RESTService.mocks

object IndexDotHtmlTestTemplate {
  // b66ff55ce83d4555acea49cc2dc5d26a$4c99b1ca2b825dfc2e311c49f3572327a7c77e8d
  import scalatags.Text.all._
  import scalatags.Text.tags2.title

  val jsModulesName          = "layer_V_JS_client"
  val jsModulesNameLowerCase = jsModulesName.map( x => x.toLower )

  def txt(testClient: Boolean ) = {

    val testString= if (testClient)  "-test-" else "-"

    s"<!DOCTYPE html>" +
      html(
        head(
          title( "Example Scala.js application" ),
          meta( httpEquiv := "Content-Type", content := "text/html; charset=UTF-8" ),
          link(
            rel := "stylesheet",
            media := "screen",
            href := "./www/assets/stylesheets/react-sortable-hoc-styles.css"
          ),
          link( rel := "stylesheet", media := "screen", href := "./www/assets/stylesheets/general.css" ),
          link( rel := "stylesheet", media := "screen", href := "./www/assets/stylesheets/ReactCrop.css" )
          //          link(rel:="stylesheet",media:="screen",href:="./server/target/web/less/main/stylesheets/main.min.css")

          //  <link rel="stylesheet"    media= "screen"   href=@_asset("stylesheets/react-sortable-hoc-styles.css") >
        ),
        body( margin := 0 )(
          div( id := "rootComp" ),
//          div( id := "jsReactComp" ),
//          div( id := "jsReactCrop" ),
          script( `type` := "text/javascript", src := "./node/generated.js/index-bundle.js" ),
          script(
            `type` := "text/javascript",
            src := s"./${jsModulesName}/target/scala-2" +
              s".11/$jsModulesNameLowerCase${testString}fastopt.js"
          ),
          script(
            `type` := "text/javascript",
            src := s"./${jsModulesName}/target/scala-2" +
              s".11/$jsModulesNameLowerCase${testString}jsdeps.js"
          ),
          script( "app.client.Main().main()" ) //, //start scalajs-react app
          //          script("console.log('pina 42')") //start scalajs-react app
        )
      )
  }
}
