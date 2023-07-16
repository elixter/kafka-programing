package com.elixter.kafka.connect

import mu.KLogging
import org.apache.kafka.connect.data.Schema
import org.apache.kafka.connect.errors.ConnectException
import org.apache.kafka.connect.source.SourceRecord
import org.apache.kafka.connect.source.SourceTask
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

class SingleFileSourceTask : SourceTask() {

    private lateinit var fileNamePartition: MutableMap<String, String>
    private lateinit var offset: MutableMap<String, Any>
    private lateinit var topic: String
    private lateinit var file: String
    private var position: Long = -1

    override fun version(): String = "1.0"

    override fun start(props: MutableMap<String, String>?) {
        runCatching {
            // Init var
            val config = SingleFileSourceConnectorConfig(props!!)
            topic = config.getString(SingleFileSourceConnectorConfig.TOPIC_NAME)
            file = config.getString(SingleFileSourceConnectorConfig.DIR_FILE_NAME)
            fileNamePartition = mutableMapOf(FILENAME_FIELD to file)
            offset = context.offsetStorageReader().offset(fileNamePartition)

            // Get file offset from offsetStorageReader
            position = offset[POSITION_FIELD]?.let {
                it as Long
            } ?: 0
        }.onFailure { e ->
            throw ConnectException(e.message, e)
        }
    }

    override fun stop() {}

    override fun poll(): MutableList<SourceRecord> {
        val results = mutableListOf<SourceRecord>()
        runCatching {
            Thread.sleep(1000)

            val lines = getLines(position)

            if (lines.isNotEmpty()) {
                lines.forEach {
                    val sourceOffset = mapOf(POSITION_FIELD to ++position)
                    val sourceRecord = SourceRecord(fileNamePartition, sourceOffset, topic, Schema.STRING_SCHEMA, it)
                    results.add(sourceRecord)
                }
            }
        }.onFailure { e ->
            logger.error(e) { e.message }
            throw ConnectException(e.message, e)
        }

        return results
    }

    private fun getLines(readLine: Long): List<String> {
        val reader = Files.newBufferedReader(Paths.get(file))
        return reader.lines().skip(readLine).collect(Collectors.toList())
    }

    companion object : KLogging() {
        const val FILENAME_FIELD = "filename"
        const val POSITION_FIELD = "position"
    }
}
