package com.elixter.kafka.connect

import org.apache.kafka.common.config.ConfigDef
import org.apache.kafka.connect.connector.Task
import org.apache.kafka.connect.sink.SinkConnector

/**
 * 사용자가 지정한 클래스 이름은 커넥트에서 호출할 때 사용되므로
 * 명확하게 어떻게 사용되는 커넥터인지 알 수 있는 이름을 사용하는 것이 좋다.
 */
class TestSinkConnector : SinkConnector() {

    override fun version(): String {
        // 싱크 커넥터 버전
        TODO("Not yet implemented")
    }

    override fun start(props: MutableMap<String, String>?) {
        // JSON 또는 Config 파일 형태로 입력한 설정값을 초기화.
        // 올바른 값이 아니라면 ConnectException()을 호출
        TODO("Not yet implemented")
    }

    override fun taskClass(): Class<out Task> {
        // 이 커넥터가 사용할 태스크 클래스 지정
        TODO("Not yet implemented")
    }

    override fun taskConfigs(maxTasks: Int): MutableList<MutableMap<String, String>> {
        // 태스크 개수가 2개 이상인 경우 태스크마다 각기 다른 옵션을 설정할 때 싸용
        TODO("Not yet implemented")
    }

    override fun stop() {
        // 커넥트가 종료될 때 필요한 로직
        TODO("Not yet implemented")
    }

    override fun config(): ConfigDef {
        // 커넥터가 사용할 설정값에 대한 정보를 받는다.
        // ConfigDev 클래스를 통해 각 설정의 이름, 기본값, 중요도, 설명을 정의할 수 있다.
        TODO("Not yet implemented")
    }
}