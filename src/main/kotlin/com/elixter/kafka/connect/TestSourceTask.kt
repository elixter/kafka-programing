package com.elixter.kafka.connect

import org.apache.kafka.connect.source.SourceRecord
import org.apache.kafka.connect.source.SourceTask

class TestSourceTask : SourceTask() {

    override fun version(): String {
        // 태스크 버전 반환. Connector와 동일한 버전으로 작성하는게 일반적
        TODO("Not yet implemented")
    }

    override fun start(props: MutableMap<String, String>?) {
        // 태스크가 시작할 때 필요한 로직 수행.
        // 데이터 처리시 필요한 리소스를 초기화 하면 좋다. (JDBC 커넥션 등...)
        TODO("Not yet implemented")
    }

    override fun stop() {
        // 태스크가 종료될 때 필요한 로직 수행.
        TODO("Not yet implemented")
    }

    override fun poll(): MutableList<SourceRecord> {
        // 소스 앱 또는 소스 파일로 부터 데이터를 읽는다.
        // 읽어온 데이터를 SourceRecord로 정의 리턴된 리스트는 토픽으로 전송.
        TODO("Not yet implemented")
    }
}