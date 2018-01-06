package app.client.ui.pages.listOfLineLists

import app.client.cache.CacheMap
import app.client.ui.pages.UserLineListsType
import app.shared.model.User
import app.shared.model.ref.Ref
import japgolly.scalajs.react.ReactElement
import japgolly.scalajs.react.{BackendScope, ReactComponentB}

object UserLineLists {
  import app.client.ui.pages.Types._

  type Prop  = Ref[User]

  type Props = PropsHolder[Prop, UserLineListsType.type]

  class Backend($ : BackendScope[Props, Unit] ) {


    import japgolly.scalajs.react.vdom.prefix_<^._

    def render(props: Props ): ReactElement = {
      val c: CacheMap = props.cache
      <.div(
        "cache:",
        <.br,
        pprint.apply( c.map ).plainText
         )
    }

  }

  val compConstr: CompConstr[UserLineListsType.type, Prop] =
    ReactComponentB[Props](
      "wrapped " +
        "page component"
    ).backend[Backend]( new Backend( _ ) ).renderBackend.build

}
