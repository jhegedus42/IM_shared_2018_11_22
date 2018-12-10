package app.client.ui.css

import scalacss.Defaults._
import scalacss.internal.mutable.GlobalRegistry
import app.client.ui.components.{LeftNav, TopNav}
import app.client.ui.pages.{HomePage, ItemsPage}

object AppCSS {

  def load = {
    GlobalRegistry.register(GlobalStyle,
                            TopNav.Style,
                            LeftNav.Style,
                            ItemsPage.Style,
                            HomePage.Style)
    GlobalRegistry.onRegistration(_.addToDocument())
  }
}
