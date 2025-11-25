package com.astordev.ugc.adapter.common

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider

class CdcMessageSerializer<T> : JsonSerializer<CdcMessage<T>>() {
    override fun serialize(value: CdcMessage<T>, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeStartObject()
        gen.writeNumberField("id", value.id)
        val operationType = when(value) {
            is CdcMessage.Create -> OperationType.CREATE.name
            is CdcMessage.Update -> OperationType.UPDATE.name
            is CdcMessage.Delete -> OperationType.DELETE.name
        }
        gen.writeStringField("operationType", operationType)
        val payload = when(value) {
            is CdcMessage.Create -> value.payload
            is CdcMessage.Update -> value.payload
            is CdcMessage.Delete -> null
        }
        gen.writeObjectField("payload", payload)
        gen.writeEndObject()
    }
}