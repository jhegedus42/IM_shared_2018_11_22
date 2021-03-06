package experiments.cacheExperiments.css
import scalacss.DevDefaults._
import scalacss.ScalaCssReact._

import scalacss.internal.mutable.GlobalRegistry

object GlobalStyleAppWithCache extends StyleSheet.Inline {

  import dsl._
  style(
    unsafeRoot( "body" )(
      margin.`0`,
      padding.`0`,
      fontSize( 14.px ),
      fontFamily := "Roboto, sans-serif",
      backgroundColor.dimgrey,
      color.white
    )
  )
}

object AppWithCacheCSS {

  def load(): Unit = {
    GlobalRegistry.register( GlobalStyleAppWithCache )
    GlobalRegistry.onRegistration( _.addToDocument() )
  }
}
