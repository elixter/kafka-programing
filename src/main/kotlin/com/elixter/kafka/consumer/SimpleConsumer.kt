package com.elixter.kafka.consumer

import mu.KLogging
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.*

class SimpleConsumer {

    fun run() {
        val configs = Properties()
        configs[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = BOOTSTRAP_SERVER
        configs[ConsumerConfig.GROUP_ID_CONFIG] = GROUP_ID
        configs[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
        configs[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name

        val consumer = KafkaConsumer<String, String>(configs)
        consumer.subscribe(listOf(TOPIC_NAME))

        while (true) {
            val records = consumer.poll(Duration.ofSeconds(1))
            records.forEach {
                logger.info { "[SimpleConsumer] record: $it" }
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
    SimpleConsumer().run()
}