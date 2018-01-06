package app.client.ui.pages.line

import app.client._jsTools.{ImgDim, ImgTools}
import app.shared.model.utils.model.{Coord, LineOld, Rect}
import app.shared.model.ref.RefVal
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{BackendScope, ReactComponentB, _}
import org.scalajs.dom.html.Div
import japgolly.scalajs.react
import japgolly.scalajs.react.vdom.ReactTagOf
import org.scalajs.dom.svg.{G, SVG}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by joco on 19/03/17.
  */
object ImageWithQue_ReactComp {

  type State = Option[Images]

  case class Props(line: RefVal[LineOld], nextLine: RefVal[LineOld])

  //todo display the size of the image here ^^^

  case class Images(lineAndImgDim: (ImgDim, LineOld), nextLineAndImgDim: (ImgDim, LineOld))

  def svgImg(images: Images): ReactTagOf[Div] = {
    import japgolly.scalajs.react.vdom.{SvgTags => t}
    import japgolly.scalajs.react.vdom.{SvgAttrs => a}
    import japgolly.scalajs.react.vdom.ReactAttr

    def ass(x: Double, y: Double) = assert(Math.abs(x - y) < 0.001)

    object Lines {
      val line           = images.lineAndImgDim._2
      val lineImgDim     = images.lineAndImgDim._1
      val nextLine       = images.nextLineAndImgDim._2
      val nextLineImgDim = images.nextLineAndImgDim._1

      /**
        * relative location of the que
        */
      val lineRelativeQueLocO: Option[Rect] = line.pl.position_of_que_pointing_to_next_picture

      val nextLine_RelativeQueDefO: Option[Rect] = nextLine.pl.que_definition
    }

    def px(x: Double): String = s"${x}px"
    def p(x: Double): String  = s"${x}%"

    /**
      *
      */
    def queSvg: Option[ReactTagOf[G]] = {

      /**
        *
        * @param id
        * @param c
        * @return
        */
      def cropRelToAbs(id: ImgDim, c: Rect): Rect = {
        new Rect(x = c.x * id.w / 100,
                 w = c.w * id.w / 100,
                 y = c.y * id.h / 100,
                 h = c.h * id.h / 100)
      }

      /**
        *
        * @param queDefRect the box which defines the que in the next image
        * @param targetRect the box in the current image (source image) into which the que should fit in
        * @return position of que in current image
        */
      def f(queDefRect: Rect, targetRect: Rect): Rect = queDefRect.fitInto(targetRect)

      def tr(c: Coord)  = t.g(a.transform := f"translate(${c.x}%.3f ${c.y}%.3f )")
      def sc(s: Double) = t.g(a.transform := f"scale(${s}%.3f )")

      val nlid = Lines.nextLineImgDim
      val lid  = Lines.lineImgDim
      for {
        rqDef <- Lines.nextLine_RelativeQueDefO
        rqLoc <- Lines.lineRelativeQueLocO
        aqDef: Rect = cropRelToAbs(nlid, rqDef) // absolute position of que Def in next image
        aqLoc: Rect = cropRelToAbs(lid, rqLoc) // absolute position
        aqDefFit    = aqDef.fitInto(aqLoc)
      } yield {
        ass(aqLoc.center.x, aqDefFit.center.x)
        ass(aqLoc.center.y, aqDefFit.center.y)
        val queCut =
          t.svg(a.height := px(nlid.h), a.width := px(nlid.w))(
              t.clipPathTag(a.id := "cut")(
                  t.rect(a.x := p(rqDef.x),
                         a.y := p(rqDef.y),
                         a.height := p(rqDef.h),
                         a.width := p(rqDef.w))),
              t.image(a.xlinkHref := nlid.url.url,
                      a.height := "100%",
                      a.width := "100%",
                      ReactAttr
                        .Generic("clipPath") := "url(#cut)")
          )
        val inOrigo=tr(aqDef.center * -1)(queCut)
        val scaled=sc(aqDef.scalingFactor(aqLoc))(inOrigo)
        tr(aqLoc.center)(scaled)
      }

    }

    queSvg
      .map({ svgQue =>
        val i = Lines.lineImgDim
        <.div(
            t.svg(a.viewBox := s"0 0 ${i.w} ${i.h}")(
                t.svg(a.height := px(i.h), a.width := px(i.w))(
                    t.image(a.xlinkHref := i.url.url, a.width := "100%", a.height := "100%")
                ),
                svgQue
            ))
      })
      .getOrElse(<.div("No Que SVG. Que is not defined."))
  }

  def apply(line: RefVal[LineOld], nextLine: RefVal[LineOld]) =
    builder(Props(line = line, nextLine = nextLine))

  def builder =
    ReactComponentB[Props]("SvgImgQue")
      .initialState(Option.empty[Images])
      .renderBackend[Backend]
      .componentDidMount(scope => scope.backend.mounted(scope.props))
      .build

  class Backend($ : BackendScope[Props, State]) {
    def mounted(props: Props): Callback = initCB(props)

    def initCB(props: Props): Callback = {
      val f: Future[Images] = for {
        l  <- ImgTools.getDimension(props.line.v.getImgSrc)
        nl <- ImgTools.getDimension(props.nextLine.v.getImgSrc)
      } yield Images((l, props.line.v), (nl, props.nextLine.v))

      Callback.future(f.map((is: Images) => $.setState(Some(is))))
      // warning.js:36 Warning: setState(...): Can only update a mounted or mounting component.
      // This usually means you called setState() on an unmounted component. This is a no-op.
      // Please check the code for the SvgImgQue component.
      // todo something .... about this ?
    }

    def render(p: Props, s: State) = {
      s.map(
            images =>
              <.div(
                  svgImg(images)
            ))
        .getOrElse(<.div("No SVG. Images are not defined."))
    }
  }

}
