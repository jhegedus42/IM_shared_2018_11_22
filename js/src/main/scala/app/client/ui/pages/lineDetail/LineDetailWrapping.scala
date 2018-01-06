package app.client.ui.pages.lineDetail

import app.client.cache.wrapper.ReadAndWriteRequestQue
import app.client.ui.pages.LineDetail
import app.client.ui.pages.Types.Wrapped_CompConstr

/**
  * Created by joco on 06/01/2018.
  */
object LineDetailWrapping {
  val que = new ReadAndWriteRequestQue()

  val wrapped: Wrapped_CompConstr[LineDetail.type, LineDetail_ReactComp.Prop] =
    que.wrapper.wrapRootPage[LineDetail.type, LineDetail_ReactComp.Prop]( LineDetail_ReactComp.lineDetailConstructor )

}
