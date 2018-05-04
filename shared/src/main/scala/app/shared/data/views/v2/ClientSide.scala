package app.shared.data.views.v2

object ClientSide{


  object Cache{
    // cache Map[ViewPayLoadType]
    var map=Map[ViewParamsPayload,ViewResponsePayLoad]()

    // does not start an ajax request if there is already one under way ...
    object Conversions{

      // json to payload
      // to be called by cache to get the payload

      //      def json2Payload[V<:View](v:V)(implicit encoder:Encoder[v.PayLoad]) : String  = {
      //        ???
      //
      //      }
      //      def

      // parameters to json

    }

    object AjaxCall {
      // when returns, trigger's a re-render of react components
      // but which ones ?
      // all of them
      // so the cache needs to have a reference to the root component ...

    }

  }

}

object React{

  object DuringRender{
    // collect all the ViewParams that need to be
    // what if an ajax call comes back and wants to trigger a re-render while the react render is in progress ?
    // this won't happen because JS is single threaded

  }


}

