package com.astordev.ugc.adapter.common

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize(using = CdcMessageSerializer::class)
sealed class CdcMessage<T> {
    abstract val id: Long
    companion object {
        fun <T> of(id: Long, operationType: OperationType, payload: T? = null): CdcMessage<T> =
            when(operationType) {
                OperationType.CREATE -> Create(id, payload ?: error("Payload required"))
                OperationType.UPDATE -> Update(id, payload ?: error("Payload required"))
                OperationType.DELETE -> Delete(id)
            }

        fun <T> fromJson(json: String, clazz: Class<T>, objectMapper: ObjectMapper): CdcMessage<T> {
            val raw = objectMapper.readTree(json)
            val id = raw["id"].asLong()
            val operationType = OperationType.valueOf(raw["operationType"].asText())
            val payload: T? = raw.get("payload")?.let { objectMapper.treeToValue(it, clazz) }
            return of(id, operationType, payload)
        }
    }

    data class Create<T>(override val id: Long, val payload: T) : CdcMessage<T>()
    data class Update<T>(override val id: Long, val payload: T) : CdcMessage<T>()
    data class Delete<T>(override val id: Long) : CdcMessage<T>()
}
