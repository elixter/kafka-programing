package com.elixter.kafka.connect

import org.apache.kafka.clients.consumer.OffsetAndMetadata
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.connect.sink.SinkRecord
import org.apache.kafka.connect.sink.SinkTask

class TestSinkTask : SinkTask() {

    override fun version(): String {
        // 태스크 버전 정보
        TODO("Not yet implemented")
    }

    override fun start(props: MutableMap<String, String>?) {
        // 태스크가 시작할 때 필요한 로직 작성
        // DB 커넥션등이 필요하다면 여기서 맺는다.
        TODO("Not yet implemented")
    }

    override fun stop() {
        // 태스크 종료시 필요한 로직
        TODO("Not yet implemented")
    }

    override fun put(records: MutableCollection<SinkRecord>?) {
        // 싱크 애플리케이션 또는 싱크 파일에 저장할 데이터를 토픽에서 주기적으로 가져오는 메소드.
        // 토픽 데이터들은 여러 개의 SinkRecord를 묶어 파라미터로 사용할 수 있다.
        // SinkRecord는 토픽의 한 개 레코드이며 토픽, 파티션, 타임스탬프 등의 정보를 담고 있다.
        TODO("Not yet implemented")
    }

    override fun flush(currentOffsets: MutableMap<TopicPartition, OffsetAndMetadata>?) {
        // put() 메소드를 통해 가져온 데이터를 일정 주기로 싱크 애플리케이션 또는 싱크 파일에 저장할 때 사용하는 로직
        // JDBC 커넥션을 사용할 때 -> put()에서 데이터를 insert하고 flush() 메소드에서 commit을 하여 트랜잭션을 종료한다.
        // put()에서 레코드를 저장할 수 있다. 이 땐 flush() 메소드는 구현할 필요 없다.
        super.flush(currentOffsets)
    }
}