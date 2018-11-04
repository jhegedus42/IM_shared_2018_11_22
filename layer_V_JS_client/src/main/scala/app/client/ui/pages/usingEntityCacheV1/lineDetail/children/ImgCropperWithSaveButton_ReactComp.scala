package app.client.ui.pages.usingEntityCacheV1.lineDetail.children

import app.client.ui.generalReactComponents.wrapped_JS_Components.ImgCropReactCompFactory
import app.client.ui.generalReactComponents.wrapped_JS_Components.ImgCropReactCompFactory.JSCrop
import app.shared.data.utils.model.{ImgUrl, Rect}
import japgolly.scalajs.react
import japgolly.scalajs.react.ReactComponentC.ReqProps
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement}



/**
  * Created by joco on 09/03/17.
  */
object ImgCropperWithSaveButton_ReactComp {

  case class Props(imgUrl: ImgUrl, onSave: S => Callback)

  import scala.scalajs.js.{Any, UndefOr}

  type S = Option[Rect]

  class Backend($: BackendScope[Props, S]) {

    object Aux {
      val g: scalajs.js.Function1[JSCrop, Unit] =
        Any.fromFunction1(
          (x: JSCrop) => {
            $.setState(Some(x)).runNow()
          }
        )
      val ug: UndefOr[scalajs.js.Function1[JSCrop, Unit]] = UndefOr.any2undefOrA(g)
    }


//    def mysvg(p:Props) =    {
//      val i=p.imgUrl
//      import japgolly.scalajs.react.vdom.{SvgTags =>t}
//      import japgolly.scalajs.react.vdom.{SvgAttrs =>a}
//      import japgolly.scalajs.react.vdom.ReactAttr
//      t.svg(a.height := "800", a.width := "500")(
//        t.clipPathTag(a.id:="cut")(t.rect ( a.x:="100px", a.y:="120px",a.height:="100px", a.width:="100px")() ),
//          t.image(a.xlinkHref:= i,a.height:="250px",a.width:="250px", ReactAttr.Generic("clipPath") :="url(#cut)")
//      )
//    }

    def render(p: Props, s: S): ReactElement = {
      val ic: ImgCropReactCompFactory = ImgCropReactCompFactory(crop = s, src = p.imgUrl, onComplete = Aux.ug)
//      println("re render called, crop is:" + s)
      <.div("Hello",
        ic.createInstance(),
        <.button(" save ", ^.onClick --> p.onSave(s)),
        s"state: $s"
//        mysvg(p)
      )
    }


  }

  def ImgCropper_Builder(initialCrop: Option[Rect]): ReqProps[Props, S, Backend, react.TopNode] =
    ReactComponentB[Props]("Cropper").
      initialState(initialCrop)
      .renderBackend[Backend]
      .build

}
