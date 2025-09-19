package com.astordev.ugc.adapter.originpost

import com.astordev.ugc.CustomObjectMapper
import com.astordev.ugc.adapter.common.OperationType
import com.astordev.ugc.adapter.common.Topic
import com.astordev.ugc.port.OriginPostMessageProducePort
import com.astordev.ugc.post.model.Post
import com.astordev.ugc.post.model.PostId
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class OriginalPostMessageProduceAdapter(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val customObjectMapper: CustomObjectMapper = CustomObjectMapper()
): OriginPostMessageProducePort {
    override fun sendCreateMessage(post: Post) {
        val message = convertToMessage(post.id.long, OperationType.CREATE, post)
        this.sendMessage(message)
    }

    override fun sendUpdateMessage(post: Post) {
        val message = convertToMessage(post.id.long, OperationType.UPDATE, post)
        this.sendMessage(message)
    }

    override fun sendDeleteMessage(id: PostId) {
        val message = convertToMessage(id.long, OperationType.DELETE, null)
        this.sendMessage(message)
    }

    private fun convertToMessage(id: Long, operationType: OperationType, post: Post?): OriginalPostMessage {
        return OriginalPostMessage(
            id,
            operationType,
            post?. let {
                OriginalPostMessage.Payload(
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
        )
    }

    private fun sendMessage(message: OriginalPostMessage) {
        kafkaTemplate.send(
            Topic.ORIGINAL_POST,
            message.id.toString(),
            customObjectMapper.writeValueAsString(message)
        )
    }
}