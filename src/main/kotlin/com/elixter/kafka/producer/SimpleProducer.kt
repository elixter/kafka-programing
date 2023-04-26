package com.elixter.kafka.producer

import com.elixter.kafka.partitioner.CustomPartitioner
import mu.KLogging
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

class SimpleProducer {

    fun run() {
        val configs = Properties().also {
            it[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = BOOTSTRAP_SERVER
            it[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java.name
            it[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java.name
            it[ProducerConfig.PARTITIONER_CLASS_CONFIG] = CustomPartitioner::class.java.name
        }

        val producer = KafkaProducer<String, String>(configs)

        val messageValue = "testMessage"
        val record = ProducerRecord<String, String>(TOPIC_NAME, "Pangyo", messageValue)
        producer.send(record, ProducerAsyncCallback())
            .also {
                logger.info { "metadata: ${it.get()}, record: $record" }
            }
        producer.flush()
        producer.close()
    }

    companion object : KLogging() {

        private const val TOPIC_NAME = "test";
        private const val BOOTSTRAP_SERVER = "local.wsl:9092"
    }
}

fun main(args: Array<String>) {
    SimpleProducer().run()
}
