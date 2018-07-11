package app.modelling.getViewCommunicationModel

import java.util.concurrent.Executors

import scala.concurrent.ExecutionContext

/**
  * Created by joco on 08/07/2018.
  */
object ExecutionContexts{
  implicit val singleThreadedExecutionContext = new ExecutionContext {
    val threadPool = Executors.newFixedThreadPool(1);

    def execute(runnable: Runnable) {
      threadPool.submit(runnable)
    }

    def reportFailure(t: Throwable) {}
  }
  implicit val singleThreadedExecutionContext_SolutionTwo = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(1))

  //https://stackoverflow.com/questions/15285284/how-to-configure-a-fine-tuned-thread-pool-for-futures

}
