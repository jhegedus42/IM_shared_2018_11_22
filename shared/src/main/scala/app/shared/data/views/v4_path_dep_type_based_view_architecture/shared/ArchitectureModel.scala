package app.shared.data.views.v4_path_dep_type_based_view_architecture.shared

import io.circe._
import io.circe
import io.circe.generic._
import io.circe.generic.auto
import io.circe.generic.auto._

import scala.reflect.ClassTag
case class EndpointName(name:String)
case class JSON(asString:String)

object ArchitectureModel {


  trait View

  trait View1 extends View

  trait ViewParams[V<:View]{
    type Req
    type Resp
  }
  object ViewParams{

    type Aux[V<:View, Rq,Rsp]= ViewParams[V] {
      type Req=Rq
      type Resp=Rsp
    }
  }



  implicit object View1_ViewParams_Instance extends ViewParams[View1] {
    case class ReqView1(s:String)
    case class RespView1(s:String)

//    override type View_ = View1.type
    override type Req = ReqView1
    override type Resp = RespView1
  }

  // req and resp type defined somewhere here

  def getEndpointName[V<:View:ClassTag]():EndpointName = {
    val name=implicitly[ClassTag[V]].runtimeClass.getSimpleName
    EndpointName(name)
  }
}


// type class server side function
// that knows

