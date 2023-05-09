package com.elixter.kafka.processor

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.processor.Processor
import java.util.Properties

class SimpleKafkaProcessor {

    fun run() {
        val props = Properties().also {
            it[StreamsConfig.APPLICATION_ID_CONFIG] = APPLICATION_NAME
            it[StreamsConfig.BOOTSTRAP_SERVERS_CONFIG] = BOOTSTRAP_SERVER
            it[StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG] = Serdes.String().javaClass
            it[StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG] = Serdes.String().javaClass
        }

        val topology = Topology()
            .addSource("Source", STREAM_LOG)
            .addProcessor("Process",  { FilterProcessor() as Processor<Any, Any> }, "Source")
            .addSink("Sink", STREAM_LOG_FILTER, "Process")

        val streaming = KafkaStreams(topology, props)
        streaming.start()
    }

    companion object {
        private const val APPLICATION_NAME = "processor-application"
        private const val BOOTSTRAP_SERVER = "local.wsl:9092"
        private const val STREAM_LOG = "stream_log"
        private const val STREAM_LOG_FILTER = "stream_log_filter"
    }
}

fun main(args: Array<String>) {
    SimpleKafkaProcessor().run()
}
