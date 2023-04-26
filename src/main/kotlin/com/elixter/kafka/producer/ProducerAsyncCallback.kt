package com.elixter.kafka.producer

import mu.KLogging
import org.apache.kafka.clients.producer.Callback
import org.apache.kafka.clients.producer.RecordMetadata
import java.lang.Exception

class ProducerAsyncCallback : Callback {

    override fun onCompletion(metadata: RecordMetadata?, exception: Exception?) {
        exception?.also {
            logger.error(it) { "[ProducerAsyncCallback] message: ${it.message}" }
        } ?: logger.info { "[ProducerAsyncCallback] metadata: $metadata" }
    }

    companion object : KLogging()
}