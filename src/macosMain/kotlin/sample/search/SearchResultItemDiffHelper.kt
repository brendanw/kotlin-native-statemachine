package sample.search

class SearchResultItemDiffHelper(private val newList: List<SearchResult>,
                                 private val oldList: List<SearchResult>) : DiffCallback() {
  override fun getOldListSize(): Int {
    return oldList.size
  }

  override fun getNewListSize(): Int {
    return newList.size
  }

  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    val itemA = oldList[oldItemPosition]
    val itemB = newList[newItemPosition]
    val result = itemA._id == itemB._id
    return result
  }

  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    val itemA = oldList[oldItemPosition]
    val itemB = newList[newItemPosition]
    val result = itemA._id == itemB._id
    return result
  }
}

abstract class DiffCallback {
  abstract fun getOldListSize(): Int

  abstract fun getNewListSize(): Int

  abstract fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean

  abstract fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean

  open fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
    return null
  }
}
