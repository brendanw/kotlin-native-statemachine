package sample.search

class SearchView {
    init {
        val viewModel = SearchViewModel()
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
