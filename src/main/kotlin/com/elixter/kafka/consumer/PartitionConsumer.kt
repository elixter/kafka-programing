package com.elixter.kafka.consumer

import mu.KLogging
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.consumer.OffsetAndMetadata
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.errors.WakeupException
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.*

class PartitionConsumer<K, V>(
    val consumer: KafkaConsumer<K, V>
) {

    fun run() {
        consumer.use { consumer ->
            runCatching {
                while (true) {
                    val records = consumer.poll(Duration.ofSeconds(1))
                    records.forEach {
                        logger.info { "[SyncCommitConsumer] record: $it" }
                    }
                    consumer.commitAsync { offsets, exception ->
                        exception?.let {
                            logger.error(it) { "Commit failed for offsets $offsets" }
                        } ?: logger.info { "Commit succeeded" }
                    }
                }
            }.onFailure { e ->
                if (e is WakeupException) {
                    logger.warn(e) { "WakeUp Consumer" }
                }
            }
        }
    }

    companion object : KLogging() {
        const val BOOTSTRAP_SERVER = "local.wsl:9092"
        const val GROUP_ID = "test-group"
    }
}

fun main() {
    val configs = Properties()
    configs[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = PartitionConsumer.BOOTSTRAP_SERVER
    configs[ConsumerConfig.GROUP_ID_CONFIG] = PartitionConsumer.GROUP_ID
    configs[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
    configs[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
    configs[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = false

    val consumer = KafkaConsumer<String, String>(configs)
    PartitionConsumer(consumer).run()
    Runtime.getRuntime().addShutdownHook(object : Thread() {
        override fun run() {
            consumer.wakeup()
        }
    })
}