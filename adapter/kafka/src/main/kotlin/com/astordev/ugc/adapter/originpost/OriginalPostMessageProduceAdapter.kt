package com.astordev.ugc.adapter.originpost

import com.astordev.ugc.adapter.common.CdcMessage
import com.astordev.ugc.adapter.common.OperationType
import com.astordev.ugc.adapter.common.Topic
import com.astordev.ugc.port.OriginPostMessageProducePort
import com.astordev.ugc.post.model.Post
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class OriginalPostMessageProduceAdapter(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val customObjectMapper: ObjectMapper
): OriginPostMessageProducePort {
    override fun sendCreateMessage(post: Post) {
        val message = CdcMessage.of(post.id.long, OperationType.CREATE, convertToPayload(post))
        this.sendMessage(message)
    }

    override fun sendUpdateMessage(post: Post) {
        val message = CdcMessage.of(post.id.long, OperationType.UPDATE, convertToPayload(post))
        this.sendMessage(message)
    }

    override fun sendDeleteMessage(post: Post) {
        val message = CdcMessage.of<OriginalPostMessagePayload>(post.id.long, OperationType.DELETE)
        this.sendMessage(message)
    }

    private fun convertToPayload(post: Post): OriginalPostMessagePayload {
        return OriginalPostMessagePayload(
            post.id,
            post.title,
            post.content,
            post.userId,
            post.categoryId,
            post.createdAt,
            post.updatedAt,
            post.deletedAt
        )
    }

    private fun sendMessage(message: CdcMessage<OriginalPostMessagePayload>) {
        kafkaTemplate.send(
            Topic.ORIGINAL_POST,
            message.id.toString(),
            customObjectMapper.writeValueAsString(message)
        )
    }
}