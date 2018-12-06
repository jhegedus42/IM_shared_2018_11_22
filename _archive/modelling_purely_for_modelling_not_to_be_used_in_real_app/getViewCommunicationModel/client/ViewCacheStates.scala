package app.modelling_purely_for_modelling_not_to_be_used_in_real_app.getViewCommunicationModel.client

import app.modelling_purely_for_modelling_not_to_be_used_in_real_app.getViewCommunicationModel.shared.views.View


/**
  * Created by joco on 02/06/2018.
  */
object ViewCacheStates{

  sealed trait ViewCacheState[V<:View]

  case class LoadingCacheState[V<:View](par:V#Par ) extends ViewCacheState[V]

  case class FailedToLoad[V<:View](par: V#Par ) extends ViewCacheState[V]

  case class LoadedAndValid[V<:View](res:V#Res) extends ViewCacheState[V]

  case class Invalid[V<:View](res:V#Res) extends ViewCacheState[V]

  case class RefreshingAfterCacheInvalidation[V<:View](res:V#Res) extends ViewCacheState[V]

  case class FailedToRefresh[V<:View](res:V#Res) extends ViewCacheState[V]


}
