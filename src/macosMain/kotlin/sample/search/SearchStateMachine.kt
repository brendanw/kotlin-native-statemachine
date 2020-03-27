package sample.search

import kotlinx.atomicfu.AtomicRef
import kotlinx.atomicfu.atomic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import sample.db.ExitDb
import sample.kotlinDb
import sample.statemachine.OnBindStateMachine
import sample.view.KDiffUtil

class SearchStateMachine(
  scope: CoroutineScope,
  private val db: ExitDb = kotlinDb
) {
  private val stateMachine = OnBindStateMachine(
    scope = scope,
    initialState = Search.State(Search.StateType.InitialState, emptyList(), null),
    reducer = ::reducer,
    sideEffects = listOf(
      ::queryDatabase,
      ::filterDatabase
    )
  )
  val viewState = stateMachine.viewState
  val dispatchAction = stateMachine::dispatchAction

  // Needed for android so we can generate diffResult.
  var lastList: AtomicRef<List<SearchResult>> = atomic(listOf())

  private fun filterDatabase(input: Flow<Search.Action>, state: () -> Search.State): Flow<Search.Action> =
    input.filterIsInstance<Search.Action.FilterUpdateAction>()
      .map { action ->
        performFilterSearch(action.filterState, action.query)
      }

  private fun queryDatabase(input: Flow<Search.Action>, state: () -> Search.State): Flow<Search.Action> =
    input.filterIsInstance<Search.Action.QueryChangeAction>()
      .map { action ->
        performQuerySearch(action.filterState, action.query)
      }

  private suspend fun performFilterSearch(filterState: FilterState, query: String) = withContext(Dispatchers.Default) {
    val searchResultList = db.getItems().map { SearchResult(name = it.name) }
    val prevList = lastList.value
    val diffResult = KDiffUtil.calculateDiff(
      SearchResultItemDiffHelper(
        newList = searchResultList,
        oldList = prevList
      )
    )
    Search.Action.SearchLoadedAction(searchResultList, diffResult)
  }

  private suspend fun performQuerySearch(filterState: FilterState, query: String) = withContext(Dispatchers.Default) {
    val newList = db.getItems().map { SearchResult(name = it.name) }
    val prevList = lastList.value
    val diffResult = KDiffUtil.calculateDiff(
      SearchResultItemDiffHelper(
        newList = newList,
        oldList = prevList
      )
    )
    Search.Action.SearchLoadedAction(newList, diffResult)
  }

  private suspend fun reducer(state: Search.State, action: Search.Action): Search.State {
    println("reducer: curState=$state action=$action")
    return when (action) {
      is Search.Action.InitializeFiltersAction -> state
      is Search.Action.FilterOptionsLoadedAction -> {
        state.copy(type = Search.StateType.FilterOptionsLoaded, filterOptions = action.filterOptions)
      }
      is Search.Action.TapFilterCancelBtn -> state.copy(
        type = Search.StateType.CloseFilterState,
        isFilterWindowVisible = false
      )
      is Search.Action.TapOpenFilterIcon -> {
        println("returning OpenFilterState")
        state.copy(type = Search.StateType.OpenFilterState, isFilterWindowVisible = true)
      }
      is Search.Action.QueryChangeAction -> state
      is Search.Action.BackButtonTapAction -> {
        if (state.isFilterWindowVisible) {
          state.copy(type = Search.StateType.CloseFilterState, isFilterWindowVisible = false)
        } else {
          state.copy(type = Search.StateType.ClosePageState, isFilterWindowVisible = false)
        }
      }
      is Search.Action.FilterUpdateAction -> state.copy(
        type = Search.StateType.CloseFilterState,
        isFilterWindowVisible = false
      )
      is Search.Action.SearchLoadedAction -> {
        lastList.value = action.items
        state.copy(type = Search.StateType.ShowResultsState, items = action.items, diffResult = action.diffResult)
      }
    }
  }
}
