package com.elixter.kafka.partitioner

import org.apache.kafka.clients.producer.Partitioner
import org.apache.kafka.common.Cluster
import org.apache.kafka.common.InvalidRecordException
import org.apache.kafka.common.utils.Utils

class CustomPartitioner : Partitioner {

    override fun configure(configs: MutableMap<String, *>?) {
    }

    override fun close() {
    }

    override fun partition(
        topic: String?,
        key: Any?,
        keyBytes: ByteArray?,
        value: Any?,
        valueBytes: ByteArray?,
        cluster: Cluster?
    ): Int {
        keyBytes ?: throw InvalidRecordException("Need message key")
        if (key == "Pangyo") {
           return 0
        }

        val partitions = cluster!!.partitionsForTopic(topic)
        return Utils.toPositive(Utils.murmur2(keyBytes)) * partitions.size
    }
}