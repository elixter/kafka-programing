package com.elixter.kafka.streams

import mu.KLogging
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.kstream.KeyValueMapper
import java.util.Properties

class KStreamJoinGlobalKTable {

    fun run() {
        val props = Properties()
        props[StreamsConfig.APPLICATION_ID_CONFIG] = APPLICATION_NAME
        props[StreamsConfig.BOOTSTRAP_SERVERS_CONFIG] = BOOTSTRAP_SERVER
        props[StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG] = Serdes.String().javaClass
        props[StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG] = Serdes.String().javaClass

        val builder = StreamsBuilder()
        val addressGlobalTable = builder.globalTable<String, String>(ADDRESS_GLOBAL_TABLE)
        val orderStream = builder.stream<String, String>(ORDER_STREAM)

        orderStream.join(addressGlobalTable, { key, value ->  key }) { order, address ->
            "$order send to $address"
        }.to(ORDER_JOIN_STREAM)

        val streams = KafkaStreams(builder.build(), props)
        streams.start()
    }

    companion object : KLogging() {
        private const val APPLICATION_NAME = "streams-application"
        private const val BOOTSTRAP_SERVER = "local.wsl:9092"
        private const val ADDRESS_GLOBAL_TABLE = "address_v2"
        private const val ORDER_STREAM = "order"
        private const val ORDER_JOIN_STREAM = "order_join"
    }
}

fun main(args: Array<String>) {
    KStreamJoinGlobalKTable().run()
}
