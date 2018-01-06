package _learning.interop.wrappers

import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react._

import scala.scalajs.js
import scala.scalajs.js.Any.fromFunction1

import scala.scalajs



  @js.native
  trait Obj extends js.Object {
    val v: String = js.native
  }

  case class EditableText_Factory(text: String, handler: String => Unit) {

    lazy val h: (String => Unit) => js.Function1[Obj, Unit] = (g) => fromFunction1((o: Obj) => {
      println("log :" + o.v);
      g(o.v);
    })

    def create(children: ReactNode*): ReactComponentU_ = {
      val toJS = {
        val p = js.Dynamic.literal();
        p.updateDynamic("text")(text);
        p.paramName = "v";
       // p.stopPropagation= true;
        p.change = h(handler)
        p
      }
      val ile=js.Dynamic.global.InlineEdit.default // default is needed because of https://github.com/kaivi/ReactInlineEdit/blob/cc7cfa28f669f45b506d4e3c969314081e173708/index.js#L190
      val f = React.asInstanceOf[js.Dynamic].createFactory(ile)
      f(toJS, children.toJsArray).asInstanceOf[ReactComponentU_]
    }
  }


  object EditableTextCompWithState {

    class Backend($: BackendScope[Unit, String]) {
      def handler(s: String): Unit  =
      {
          println("handler:"+s)
          val c: Unit =$.setState(s).runNow()
          c
      }

      def render(s:String) = {
        println("state:"+s)
        <.div(
          <.span(s),<.br,
          EditableText_Factory(s, handler _).create(),
          <.button(" print state",  ^.onClick -->  Callback({println("state: "+s)}))

        )
      }
    }

    val getCompBuilder = (s:String)=>ReactComponentB[Unit]("EditableText with state").initialState(s)
          .renderBackend[Backend].build
  }


