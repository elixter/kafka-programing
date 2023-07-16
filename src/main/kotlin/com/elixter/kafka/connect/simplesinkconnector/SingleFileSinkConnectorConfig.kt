package com.elixter.kafka.connect.simplesinkconnector

import org.apache.kafka.common.config.AbstractConfig
import org.apache.kafka.common.config.ConfigDef

class SingleFileSinkConnectorConfig(
    val props: Map<String, String>
) : AbstractConfig(CONFIG, props) {

    companion object {
        internal const val DIR_FILE_NAME = "file"
        private const val DIR_FILE_NAME_DEFAULT_VALUE = "/tmp/kafka.txt"
        private const val DIR_FILE_NAME_DOC = "저장할 디렉토리와 파일 이름"
        val CONFIG: ConfigDef = ConfigDef().define(
            DIR_FILE_NAME,
            ConfigDef.Type.STRING,
            DIR_FILE_NAME_DEFAULT_VALUE,
            ConfigDef.Importance.HIGH,
            DIR_FILE_NAME_DOC
        )
    }
}