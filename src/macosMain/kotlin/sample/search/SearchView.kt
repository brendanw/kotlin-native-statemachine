package sample.search

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sample.search.FilterState
import sample.search.Search
import sample.search.SearchViewModel

class SearchView {
  private val viewModel = SearchViewModel()

  init {
    GlobalScope.launch {
      viewModel.dispatchAction(
        Search.Action.QueryChangeAction(
          filterState = FilterState(),
          query = "F"
        )
      )

      viewModel.dispatchAction(
        Search.Action.QueryChangeAction(
          filterState = FilterState(),
          query = "Fo"
        )
      )
    }
  }

}
