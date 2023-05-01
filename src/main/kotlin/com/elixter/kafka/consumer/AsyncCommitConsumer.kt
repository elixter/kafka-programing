package com.elixter.kafka.consumer

import mu.KLogging
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.consumer.OffsetAndMetadata
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.*

class AsyncCommitConsumer {

    fun run() {
        val configs = Properties()
        configs[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = BOOTSTRAP_SERVER
        configs[ConsumerConfig.GROUP_ID_CONFIG] = GROUP_ID
        configs[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
        configs[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
        configs[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = false

        val consumer = KafkaConsumer<String, String>(configs)
        val currentOffset: MutableMap<TopicPartition, OffsetAndMetadata> = mutableMapOf()
        consumer.subscribe(listOf(TOPIC_NAME), object : ConsumerRebalanceListener{
            override fun onPartitionsAssigned(partitions: MutableCollection<TopicPartition>?) {
                logger.warn { "Partitions are assigned" }
            }

            override fun onPartitionsRevoked(partitions: MutableCollection<TopicPartition>?) {
                logger.warn("Partitions are revoked")
                consumer.commitSync(currentOffset)
            }
        })

        while (true) {
            val records = consumer.poll(Duration.ofSeconds(1))
            records.forEach {
                logger.info { "[SyncCommitConsumer] record: $it" }
                currentOffset[TopicPartition(it.topic(), it.partition())] = OffsetAndMetadata(it.offset(), null)
//                consumer.commitSync(
//                    mapOf(
//                        TopicPartition(it.topic(), it.partition()) to OffsetAndMetadata(it.offset() + 1, null)
//                    )
//                )
            }
            consumer.commitAsync { offsets, exception ->
                exception?.let {
                    logger.error(it) { "Commit failed for offsets $offsets" }
                } ?: logger.info { "Commit succeeded" }
            }
        }
    }

    companion object : KLogging() {
        private const val TOPIC_NAME = "test"
        private const val BOOTSTRAP_SERVER = "local.wsl:9092"
        private const val GROUP_ID = "test-group"
    }
}

fun main(args: Array<String>) {
    AsyncCommitConsumer().run()
}