package experiments.cacheExperiments.cache

/**
  * This is used by the [[AJAXReqInFlightMonitor]] to cause a re-render
  * of the react tree if needed.
  *
  * This is created in [[experiments.cacheExperiments.components.RootComp]] by the
  *  [[experiments.cacheExperiments.components.RootComp.toBeCalledByComponentDidMount()]]
  *  method when component did mount (what that really means, is a mystery).
  *
  *  Some answer can be found in
  *  [[https://learn.co/lessons/react-component-mounting-and-unmounting]].
  *
  * @param triggerReRender
  */
case class ReRenderTriggerer(triggerReRender: Unit => Unit )
