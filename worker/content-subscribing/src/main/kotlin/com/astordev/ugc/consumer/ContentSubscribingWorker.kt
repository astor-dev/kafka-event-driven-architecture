package com.astordev.ugc.consumer

import com.astordev.ugc.CustomObjectMapper
import com.astordev.ugc.SubscribingPostAddToInboxUseCase
import com.astordev.ugc.SubscribingPostRemoveFromInboxUseCase
import com.astordev.ugc.adapter.common.OperationType
import com.astordev.ugc.adapter.common.Topic
import com.astordev.ugc.adapter.inspectedpost.InspectedPostMessage
import com.astordev.ugc.post.model.PostId
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ContentSubscribingWorker(
    private val subscribingPostAddToInboxUseCase: SubscribingPostAddToInboxUseCase,
    private val subscribingPostRemoveFromInboxUseCase: SubscribingPostRemoveFromInboxUseCase
) {
    private val objectMapper = CustomObjectMapper()

    @KafkaListener(
        topics = [ Topic.INSPECTED_POST ],
        groupId = "subscribing-post-consumer-group",
        concurrency = "3"
    )
    fun listen(message: ConsumerRecord<String, String>) {
        val inspectedPostMessage = objectMapper.readValue(message.value(), InspectedPostMessage::class.java)
        when (inspectedPostMessage.operationType) {
            OperationType.CREATE -> {
                subscribingPostAddToInboxUseCase.saveSubscribingInboxPost(inspectedPostMessage.payload.post)
            }
            OperationType.UPDATE -> {
                // DO_NTH
            }
            OperationType.DELETE -> {
                subscribingPostRemoveFromInboxUseCase.deleteSubscribingInboxPost(PostId.from(inspectedPostMessage.id))
            }
        }
    }
}