package sample.search

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchView {
    init {
        val viewModel = SearchViewModel()

        GlobalScope.launch {
            viewModel.dispatchAction(
                Search.Action.QueryChangeAction(
                    filterState = FilterState(),
                    query = "F"
                )
            )

            delay(500)

            viewModel.dispatchAction(
                Search.Action.QueryChangeAction(
                    filterState = FilterState(),
                    query = "Fo"
                )
            )

            delay(1000)

            viewModel.dispatchAction(
                Search.Action.QueryChangeAction(
                    filterState = FilterState(),
                    query = "For"
                )
            )
        }
    }
}
