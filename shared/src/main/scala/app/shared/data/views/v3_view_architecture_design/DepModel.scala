package app.shared.data.views.v3_view_architecture_design
import app.shared.data.views.v3_view_architecture_design.shared.views.View1_HolderObject
import app.shared.data.views.v3_view_architecture_design.shared.views.View1_HolderObject.{ReqParams_View1, Response_View1, View1}
import app.shared.data.views.v3_view_architecture_design.shared.JSON_ToFrom_ReqResp_Convert
import io.circe.Error
import io.circe.generic.auto._

object TestJSONConvert extends App{

  println("fax")

  val v1res= View1_HolderObject.Response_View1("bla")
  val sres: String = JSON_ToFrom_ReqResp_Convert.encodeResponseToJSON(v1res)

  val v1resp_either: Either[Error, Response_View1] =
    JSON_ToFrom_ReqResp_Convert.decodeJSONToResponse[View1,Response_View1](sres)
  println(sres)
  println(v1resp_either)

  val v1req: ReqParams_View1 = View1_HolderObject.ReqParams_View1("req")
  val sreq : String          = JSON_ToFrom_ReqResp_Convert.encodeRequestToJSON(v1req)

  val v1req_either: Either[Error, ReqParams_View1] =
    JSON_ToFrom_ReqResp_Convert.decodeJSONToRequest[View1,ReqParams_View1](sreq)


  println(sreq)
  println(v1req_either)

}


// todo make whole chain in the model ...




