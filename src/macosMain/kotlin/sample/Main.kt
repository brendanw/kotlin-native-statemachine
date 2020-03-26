package sample

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.broadcast
import platform.CoreFoundation.CFRunLoopRun
import sample.search.SearchView

fun main() {
    SearchView()
    CFRunLoopRun()
}

fun <T> Channel<T>.multicast(scope: CoroutineScope): BroadcastChannel<T> {
    val channel = this
    return scope.broadcast {
        for (x in channel) {
            offer(x)
        }
    }
}
