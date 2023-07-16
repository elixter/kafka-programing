package com.elixter.kafka.connect.simplesinkconnector

import org.apache.kafka.common.config.ConfigDef
import org.apache.kafka.connect.connector.Task
import org.apache.kafka.connect.errors.ConnectException
import org.apache.kafka.connect.sink.SinkConnector
import kotlin.math.max

class SingleFileSinkConnector() : SinkConnector() {

    private lateinit var configProperties: Map<String, String>

    override fun version(): String = "1.0"

    override fun start(props: MutableMap<String, String>) {
        configProperties = props
        kotlin.runCatching {
            SingleFileSinkConnectorConfig(props)
        }.onFailure { e ->
            throw ConnectException(e.message, e)
        }
    }

    override fun taskClass(): Class<out Task> = SingleFileSinkTask::class.java

    override fun taskConfigs(maxTasks: Int): MutableList<MutableMap<String, String>> {
        val taskConfigs = mutableListOf<MutableMap<String, String>>()
        val taskProps = mutableMapOf<String, String>().also { it.putAll(configProperties) }


        for (i in 0 until  maxTasks) {
            taskConfigs.add(taskProps)
        }

        return taskConfigs
    }

    override fun stop() {
    }

    override fun config(): ConfigDef = SingleFileSinkConnectorConfig.CONFIG
}