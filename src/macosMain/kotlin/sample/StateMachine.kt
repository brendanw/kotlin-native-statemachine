package sample

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

class StateWrapper<T>(var state: T)

class StateMachine<R : Any, T>(
  val scope: CoroutineScope,
  private val initialState: T,
  private val sideEffects: List<(Flow<R>, () -> T) -> Flow<R>>,
  private val reducer: suspend (accumulator: T, value: R) -> T
) {
  val viewState: FlowRelay<T> = FlowRelay()
  private val inputActions: Channel<R> = Channel()

  init {
    scope.launch {
      val scope = this
      val lastState = StateWrapper(initialState)
      val multicaster = inputActions.multicast(scope)
      val flowList = sideEffects.map { sideEffect ->
        sideEffect(multicaster.asFlow(), { lastState.state })
      }.run {
        toMutableList().apply {
          add(multicaster.asFlow())
        }
      }
      flowList.merge()
        .onEach { println("result $it") }
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
    inputActions.send(action)
  }
}
