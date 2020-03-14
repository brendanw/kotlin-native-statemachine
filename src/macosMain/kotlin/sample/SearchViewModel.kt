package sample

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class SearchViewModel : CoroutineScope {
  private val job = Job()
  override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + job

  private val searchStateMachine = SearchStateMachine(this)
  val stateFlow: FlowRelay<Search.State>
    get() {
      return searchStateMachine.viewState
    }

  fun dispatchAction(action: Search.Action) {
    searchStateMachine.dispatchAction(action)
  }

  fun clear() {
    job.cancel()
  }
}
