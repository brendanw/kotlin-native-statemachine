package sample

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
