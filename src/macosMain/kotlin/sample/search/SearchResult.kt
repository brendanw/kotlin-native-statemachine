package sample.search

data class SearchResult(
  val _id: String,
  val name: String,
  val city: String,
  val country: String,
  val region: String,
  val latLng: LatLng,
  val totalFlyableAltitude: Int,
  val distanceToTalus: Int,
  val hikeTimeMins: Int,
  val hikeTime: String
)

data class LatLng(
  val latitude: String,
  val longitude: String
)
