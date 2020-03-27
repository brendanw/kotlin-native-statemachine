package sample

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.broadcast
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import platform.CoreFoundation.CFRunLoopRun
import sample.db.ExitDb
import sample.search.SearchView

val kotlinDb = ExitDb()

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
