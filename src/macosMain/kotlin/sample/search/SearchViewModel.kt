package sample.search

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import sample.flow.FlowRelay
import sample.search.list.Search
import kotlin.coroutines.CoroutineContext


class SearchViewModel : CoroutineScope {
  private val job = Job()
  override val coroutineContext: CoroutineContext = Dispatchers.Main + job

  private val searchStateMachine = SearchStateMachine(this)
  val stateFlow: FlowRelay<Search.State>
    get() {
      return searchStateMachine.viewState
    }

  fun dispatchAction(action: Search.Action) {
    searchStateMachine.dispatchAction.invoke(action)
  }

  fun clear() {
    job.cancel()
  }
}
