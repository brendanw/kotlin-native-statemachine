package sample

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.broadcast
import sample.search.SearchView

val isNativeMt = true

fun main() {
  if (isNativeMt) {
    SearchView()

    while (true) {
    }
  } else {
    runBlocking {
      SearchView()
      while(true) {
        yield()
      }
    }
  }
}

fun <T> Channel<T>.multicast(scope: CoroutineScope): BroadcastChannel<T> {
  val channel = this
  return scope.broadcast {
    for (x in channel) {
      offer(x)
    }
  }
}
