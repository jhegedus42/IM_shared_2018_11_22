package app.client.ui.pages.main.root_children.materialUI_children.router_children.wrappedComponents

import app.client.cache.wrapper.ReadAndWriteRequestQue
import app.client.ui.pages.LineDetail
import app.client.ui.pages.Types.Wrapped_CompConstr
import app.client.ui.pages.line.LineDetail_ReactComp

/**
  * Created by joco on 06/01/2018.
  */
object LineDetailWrapping {
  val que = new ReadAndWriteRequestQue()

  val wrapped_lineDetail_compConstr: Wrapped_CompConstr[LineDetail.type, LineDetail_ReactComp.Prop] =
    que.wrapper.wrapRootPage[LineDetail.type, LineDetail_ReactComp.Prop](
      LineDetail_ReactComp.lineDetailConstructor
    )

}
