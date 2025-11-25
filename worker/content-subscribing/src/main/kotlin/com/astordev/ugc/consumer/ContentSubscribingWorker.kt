package com.astordev.ugc.consumer

import com.astordev.ugc.SubscribingPostAddToInboxUseCase
import com.astordev.ugc.SubscribingPostRemoveFromInboxUseCase
import com.astordev.ugc.adapter.common.CdcMessage
import com.astordev.ugc.adapter.common.Topic
import com.astordev.ugc.adapter.inspectedpost.InspectedPostMessagePayload
import com.astordev.ugc.post.model.PostId
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ContentSubscribingWorker(
    private val subscribingPostAddToInboxUseCase: SubscribingPostAddToInboxUseCase,
    private val subscribingPostRemoveFromInboxUseCase: SubscribingPostRemoveFromInboxUseCase,
    private val objectMapper: ObjectMapper
) {

    @KafkaListener(
        topics = [ Topic.INSPECTED_POST ],
        groupId = "subscribing-post-consumer-group",
        concurrency = "3"
    )
    fun listen(message: ConsumerRecord<String, String>) {
        val inspectedPostMessage = CdcMessage.fromJson(message.value(), InspectedPostMessagePayload::class.java, objectMapper)
        when (inspectedPostMessage) {
            is CdcMessage.Create -> {
                subscribingPostAddToInboxUseCase.saveSubscribingInboxPost(inspectedPostMessage.payload.post)
            }
            is CdcMessage.Update -> {
                // DO_NTH
            }
            is CdcMessage.Delete -> {
                subscribingPostRemoveFromInboxUseCase.deleteSubscribingInboxPost(PostId.from(inspectedPostMessage.id))
            }
        }
    }
}