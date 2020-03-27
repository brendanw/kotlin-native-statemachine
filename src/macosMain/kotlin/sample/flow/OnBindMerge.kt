package sample.flow

import kotlinx.atomicfu.atomic
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun <T> List<Flow<T>>.onBindMerge(onBind: () -> Unit): Flow<T> {
    var boundFlows = atomic(0)
    return channelFlow {
        forEach { flow ->
            launch {
                flow.asBindFlow {
                    if (boundFlows.incrementAndGet() == size) onBind()
                }.collect {
                    send(it)
                }
            }
        }
    }
}

fun <T> Flow<T>.asBindFlow(onBind: () -> Unit): Flow<T> {
    return BindFlow(onBind = onBind, flow = this)
}

class BindFlow<T>(val onBind: () -> Unit, val flow: Flow<T>) : Flow<T> {
    private var hasBinded = atomic(false)
    @InternalCoroutinesApi
    override suspend fun collect(collector: FlowCollector<T>) {
        if (hasBinded.compareAndSet(expect = false, update = true)) {
            onBind()
        }
        flow.collect { item ->
            collector.emit(item)
        }
    }
}

