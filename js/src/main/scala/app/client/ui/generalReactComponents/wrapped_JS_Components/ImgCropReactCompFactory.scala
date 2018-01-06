package app.client.ui.generalReactComponents.wrapped_JS_Components

import app.client.ui.generalReactComponents.wrapped_JS_Components.ImgCropReactCompFactory.JSCrop
import app.shared.model.utils.model.{ImgUrl, Rect}
import japgolly.scalajs.react.{React, ReactComponentU_, ReactNode}

import scala.scalajs.js
import scala.scalajs.js.{UndefOr, undefined}

case class Shape(x:Double,y:Double,width:Double,height:Double)
object ImgCropReactCompFactory {

  @js.native
  trait JSCrop extends js.Object {
    val x: Double = js.native
    val y: Double = js.native
    val width: Double = js.native
    val height: Double = js.native
  }

  object JSCrop {
    def apply(x:Double,y:Double,width:Double,height:Double): JSCrop =
      js.Dynamic.literal(x=x, y=y, width=width, height =height).asInstanceOf[JSCrop]
  }

  implicit def toCropShared(c:JSCrop)= Rect(x= c.x, y= c.y, w= c.width, h= c.height)

  implicit def fromCropShared(oc:Option[Rect]):UndefOr[JSCrop]= oc match {
    case None => undefined
    case Some(c)=> JSCrop (x=c.x, y=c.y, height=c.h, width=c.w)
  }
  // taken from https://www.scala-js.org/doc/interoperability/types.html

}

case class ImgCropReactCompFactory (crossorigin : UndefOr[String] = undefined,
                                    minHeight : UndefOr[Int] = undefined,
                                    onChange : UndefOr[js.Function] = undefined,
                                    onImageLoaded : UndefOr[js.Function] = undefined,
                                    imageAlt : UndefOr[String] = undefined,
                                    minWidth : UndefOr[Int] = undefined,
                                    onAspectRatioChange : UndefOr[js.Function] = undefined,
                                    src : ImgUrl,
                                    crop: UndefOr[JSCrop]=undefined,
                                    disabled : UndefOr[Boolean]=undefined,
                                    keepSelection : UndefOr[Boolean]=undefined,
                                    onComplete : UndefOr[js.Function1[JSCrop,Unit]] = undefined,
                                    maxWidth : UndefOr[Int] = undefined,
                                    maxHeight : UndefOr[Int] = undefined) {

  def toJS = {
    val p = js.Dynamic.literal()
    crossorigin.foreach(v => p.updateDynamic("crossorigin")(v))
    minHeight.foreach(v => p.updateDynamic("minHeight")(v))
    onChange.foreach(v => p.updateDynamic("onChange")(v))
    onImageLoaded.foreach(v => p.updateDynamic("onImageLoaded")(v))
    imageAlt.foreach(v => p.updateDynamic("imageAlt")(v))
    minWidth.foreach(v => p.updateDynamic("minWidth")(v))
    crop.foreach(v => p.updateDynamic("crop")(v))
    onAspectRatioChange.foreach(v => p.updateDynamic("onAspectRatioChange")(v))
    p.updateDynamic("src")(src.url)
    println ("src: "+src)
    disabled.foreach(v => p.updateDynamic("disabled")(v))
    keepSelection.foreach(v => p.updateDynamic("keepSelection")(v))
    onComplete.foreach(v => p.updateDynamic("onComplete")(v))
    maxWidth.foreach(v => p.updateDynamic("maxWidth")(v))
    maxHeight.foreach(v => p.updateDynamic("maxHeight")(v))
    p
  }

  def createInstance(children : ReactNode*): ReactComponentU_ = {
    val rc=js.Dynamic.global.ReactCrop
//    println("rc:"+rc)
    val f = React.asInstanceOf[js.Dynamic].createFactory(rc)
    // access real js component , make sure you wrap with createFactory (this is needed from 0.13 onwards)
//    println("f:"+f)
    // expl anation is here : https://facebook.github.io/react/blog/2014/10/14/introducing-react-elements.html
    f(toJS, children.toJsArray).asInstanceOf[ReactComponentU_]
  }
}


