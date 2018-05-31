package app.shared.data.views.v2

object ClientSide{


  object Cache{
    // cache Map[ViewPayLoadType]
    var map=Map[ViewRequestParamsPayload,ViewResponsePayLoad]()



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

