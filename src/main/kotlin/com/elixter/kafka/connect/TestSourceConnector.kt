package com.elixter.kafka.connect

import org.apache.kafka.common.config.ConfigDef
import org.apache.kafka.connect.connector.Task
import org.apache.kafka.connect.source.SourceConnector

class TestSourceConnector : SourceConnector() {

    override fun version(): String {
        // 커넥터 버전 반환
        TODO("Not yet implemented")
    }

    override fun start(props: MutableMap<String, String>?) {
        // 사용자가 JSON 또는 config 파일 형태로 입력한 설정값을 초기화
        // ConnectException을 던져 종료할 수 있다.
        TODO("Not yet implemented")
    }

    override fun taskClass(): Class<out Task> {
        // 이 커넥터가 사용할 태스크 클래스를 지정
        TODO("Not yet implemented")
    }

    override fun taskConfigs(maxTasks: Int): MutableList<MutableMap<String, String>> {
        // 태스크 개수가 2개 이상인 경우 각기 다른 옵션을 설정할 때 사용
        TODO("Not yet implemented")
    }

    override fun stop() {
        // 커넥터 종료시 필요한 로직 수행
        TODO("Not yet implemented")
    }

    override fun config(): ConfigDef {
        // 커넥터가 사용할 설정값에 대한 정보 반환
        TODO("Not yet implemented")
    }
}