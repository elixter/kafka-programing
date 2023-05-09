package com.elixter.kafka.processor

import org.apache.kafka.streams.processor.Processor
import org.apache.kafka.streams.processor.ProcessorContext

class FilterProcessor: Processor<String, String> {

    private lateinit var context: ProcessorContext

    override fun init(context: ProcessorContext) {
        this.context = context
    }

    override fun close() {
    }

    override fun process(key: String?, value: String?) {
        if (value?.length!! > 5) {
            context.forward(key, value)
        }
        context.commit()
    }
}