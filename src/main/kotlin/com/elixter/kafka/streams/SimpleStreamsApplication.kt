package com.elixter.kafka.streams

import mu.KLogging
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import java.util.Properties

class SimpleStreamsApplication {

    fun run() {
        val props = Properties()
        props[StreamsConfig.APPLICATION_ID_CONFIG] = APPLICATION_NAME
        props[StreamsConfig.BOOTSTRAP_SERVERS_CONFIG] = BOOTSTRAP_SERVER
        props[StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG] = Serdes.String().javaClass
        props[StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG] = Serdes.String().javaClass

        val builder = StreamsBuilder()
        val streamLog = builder.stream<String, String>(STREAM_LOG)
        streamLog.to(STREAM_LOG_COPY)

        val streams = KafkaStreams(builder.build(), props)
        streams.start()
    }

    companion object : KLogging() {
        private const val APPLICATION_NAME = "streams-application"
        private const val BOOTSTRAP_SERVER = "local.wsl:9092"
        private const val STREAM_LOG = "stream_log"
        private const val STREAM_LOG_COPY = "stream_log_copy"
    }
}

fun main(args: Array<String>) {
    SimpleStreamsApplication().run()
}
