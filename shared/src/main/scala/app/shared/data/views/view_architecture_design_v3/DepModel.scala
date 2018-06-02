package app.shared.data.views.view_architecture_design_v3
import app.shared.data.views.view_architecture_design_v3.shared.views.View1_HolderObject
import app.shared.data.views.view_architecture_design_v3.shared.{JSONConvert, View}
import io.circe.generic.auto._

object TestJSONConvert extends App{

  println("fax")

  val v1res= View1_HolderObject.Response_View1("bla")
  val sres=JSONConvert.encodeResponseToJSON(v1res)

  println(sres)

  val v1req= View1_HolderObject.ReqParams_View1("req")
  val sreq=JSONConvert.encodeRequestToJSON(v1req)

  println(sreq)

}







