package sample

val defaultCountryList = listOf("Australia", "Austria", "Brazil", "Canada", "China", "France", "Greece", "New Zealand", "Switzerland", "Turkey", "United States", "Venezuela")
val exitDirectionList = listOf("north", "northeast", "east", "southeast", "south", "southwest", "west", "northwest")

class Search {

  /**
   * Defines default options for each filter section
   */
  data class FilterOptions(
    val filterCountryList: List<String> = defaultCountryList,
    val filterExitDirectionList: List<String> = exitDirectionList
  )

  sealed class Action {
    object InitializeFiltersAction : Search.Action()
    data class FilterOptionsLoadedAction(val filterOptions: FilterOptions) : Search.Action()

    object TapFilterCancelBtn : Search.Action()
    object TapOpenFilterIcon : Search.Action()
    object BackButtonTapAction : Search.Action()

    data class QueryChangeAction(
      val filterState: FilterState,
      val query: String
    ) : Search.Action()

    data class FilterUpdateAction(
      val filterState: FilterState,
      val query: String
    ) : Search.Action()

    data class SearchLoadedAction(
      val items: List<SearchResult>,
      val diffResult: DiffResult?
    ) : Search.Action()
  }

  enum class StateType {
    InitialState,
    FilterOptionsLoaded,
    ShowResultsState,
    CloseFilterState,
    ClosePageState,
    OpenFilterState
  }

  data class State(
    val type: StateType,
    val items: List<SearchResult>,
    val diffResult: DiffResult?,
    val isFilterWindowVisible: Boolean = false,
    val filterOptions: FilterOptions = FilterOptions()
  )
}

class DiffResult {

}
