package app.modelling.getViewCommunicationModel.client

import app.modelling.getViewCommunicationModel.shared.views.View


/**
  * Created by joco on 02/06/2018.
  */
object ViewCacheStates{

  sealed trait ViewCacheState[V<:View]

  case class LoadingCacheState[V<:View](par:V#Par ) extends ViewCacheState[V]

  case class FailedToLoad[V<:View](par: V#Par ) extends ViewCacheState[V]

  case class Loaded[V<:View](res:V#Res) extends ViewCacheState[V]

  case class Invalidated[V<:View](res:V#Res) extends ViewCacheState[V]

  case class Refreshing[V<:View](res:V#Res) extends ViewCacheState[V]

  case class FailedToRefresh[V<:View](res:V#Res) extends ViewCacheState[V]


}
