package sample.flow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.asFlow

/**
 * This is similar to RxRelay.
 */
class FlowRelay<T>(capacity: Int = Channel.BUFFERED) : Flow<T> {
  @InternalCoroutinesApi
  override suspend fun collect(collector: FlowCollector<T>) {
    flow.collect(collector)
  }

  @UseExperimental(ExperimentalCoroutinesApi::class)
  private val broadcastChannel = BroadcastChannel<T>(capacity)

  @UseExperimental(FlowPreview::class)
  private val flow: Flow<T> = broadcastChannel.asFlow()

  @UseExperimental(ExperimentalCoroutinesApi::class)
  suspend fun send(event: T) {
    broadcastChannel.send(event)
  }
}
