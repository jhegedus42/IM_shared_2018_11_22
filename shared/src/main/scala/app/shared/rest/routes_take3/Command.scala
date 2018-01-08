package app.shared.rest.routes_take3

//import akka.http.scaladsl.marshalling.ToResponseMarshallable
import app.shared.data.model.Entity.Data


trait Command[E<:Data] {
  type Params
  type Result
//  type E <:Entity
  def printParams(p: Params ): Unit = println(p)
  def getServerPath : String

}

















