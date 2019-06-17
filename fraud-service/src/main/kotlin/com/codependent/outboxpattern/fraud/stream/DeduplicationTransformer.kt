package com.codependent.outboxpattern.fraud.stream

import com.codependent.outboxpattern.account.TransferEmitted
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.kstream.Transformer
import org.apache.kafka.streams.processor.ProcessorContext
import org.apache.kafka.streams.state.KeyValueStore


@Suppress("UNCHECKED_CAST")
class DeduplicationTransformer : Transformer<String, TransferEmitted, KeyValue<String, TransferEmitted>> {

    private lateinit var dedupStore: KeyValueStore<String, TransferEmitted>
    private lateinit var context: ProcessorContext

    override fun init(context: ProcessorContext) {
        this.context = context
        dedupStore = context.getStateStore(DEDUP_STORE) as KeyValueStore<String, TransferEmitted>
    }

    override fun transform(key: String, value: TransferEmitted): KeyValue<String, TransferEmitted>? {
        return if (isDuplicate(key)) {
            null
        } else {
            dedupStore.put(key, value)
            KeyValue(key, value)
        }
    }

    private fun isDuplicate(key: String) = dedupStore[key] != null

    override fun close() {
    }
}
