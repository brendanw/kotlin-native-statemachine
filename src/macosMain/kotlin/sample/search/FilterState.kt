package sample.search

/**
 * Contains mapping of filter values that is consistent with what
 * the backend expects
 */
data class FilterState(
  var totalFlyableMin: Int = 0,
  var totalFlyableMax: Int = 0,
  var distanceToTalusMin: Int = 0,
  var distanceToTalusMax: Int = 0,
  var countrySet: Set<String> = emptySet(),
  var verticality: String = "",
  var exitDirectionSet: Set<String> = emptySet(),
  var hikeTimeMin: Int = 0,
  var hikeTimeMax: Int = 0,
  var seasonality: String = "",
  var sortBy: String = "totalFlyableAltitude"
)

fun emptyFilterState(): FilterState {
  return FilterState()
}
