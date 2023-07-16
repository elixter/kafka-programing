package com.elixter.kafka.connect

import org.apache.kafka.common.config.ConfigDef
import org.apache.kafka.connect.connector.Task
import org.apache.kafka.connect.errors.ConnectException
import org.apache.kafka.connect.source.SourceConnector

class SingleFileSourceConnector : SourceConnector() {

    private lateinit var configProperties: MutableMap<String, String>

    override fun version(): String = "1.0"

    override fun start(props: MutableMap<String, String>?) {
        configProperties = props!!
        runCatching {
            SingleFileSourceConnectorConfig(props)
        }.onFailure { e ->
            throw ConnectException(e.message, e)
        }
    }

    override fun taskClass(): Class<out Task> = SingleFileSourceTask::class.java

    override fun taskConfigs(maxTasks: Int): MutableList<MutableMap<String, String>> {
        val taskConfigs = mutableListOf(mutableMapOf<String, String>())
        val taskProps = mutableMapOf<String, String>()

        taskProps.putAll(configProperties)
        for (i in 0 until maxTasks) {
            taskConfigs.add(taskProps)
        }

        return taskConfigs
    }

    override fun stop() {}

    override fun config(): ConfigDef = SingleFileSourceConnectorConfig.CONFIG
}
