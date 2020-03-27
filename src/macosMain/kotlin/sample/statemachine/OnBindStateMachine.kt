package sample.statemachine

import kotlinx.atomicfu.atomic
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import sample.flow.FlowRelay
import sample.flow.onBindMerge

class OnBindStateMachine<R : Any, T>(
    val scope: CoroutineScope,
    private val initialState: T,
    private val sideEffects: List<(Flow<R>, () -> T) -> Flow<R>>,
    private val reducer: suspend (accumulator: T, value: R) -> T
) {
    val viewState: FlowRelay<T> = FlowRelay()
    private var isInitialized = atomic(false)
    private val inputActions: BroadcastChannel<R> = BroadcastChannel(Channel.BUFFERED)

    init {
        scope.launch {
            val lastState = StateWrapper(initialState)
            val flowList = sideEffects.map { sideEffect ->
                sideEffect(inputActions.asFlow(), { lastState.state })
            }.run {
                toMutableList().apply {
                    add(inputActions.asFlow())
                }
            }
            flowList.onBindMerge {
                isInitialized.value = true
            }
                .onEach { println("result $it") }
                .onCompletion {
                    println("cancelling initial inputFlow")
                    inputActions.cancel()
                }
                .scan(lastState.state, reducer)
                .distinctUntilChanged()
                .collect { outputState ->
                    lastState.state = outputState
                    viewState.send(outputState)
                }
        }
    }

    fun dispatchAction(action: R) = scope.launch {
        println("Received input action $action")
        while (!isInitialized.value) {
            yield()
        }
        inputActions.send(action)
    }
}
