package com.astordev.ugc.consumer

import com.astordev.ugc.PostIndexingUseCase
import com.astordev.ugc.adapter.common.CdcMessage
import com.astordev.ugc.adapter.common.Topic
import com.astordev.ugc.adapter.inspectedpost.InspectedPostMessagePayload
import com.astordev.ugc.adapter.inspectedpost.toModel
import com.astordev.ugc.post.model.PostId
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ContentIndexingWorker(
    private val objectMapper: ObjectMapper,
    private val postIndexingUseCase: PostIndexingUseCase
) {

    @KafkaListener(
        topics = [Topic.INSPECTED_POST ],
        groupId = "indexing-post-consumer-group",
        concurrency = "3"
    )
    fun listen(message: ConsumerRecord<String, String>) {
        val message : CdcMessage<InspectedPostMessagePayload> = CdcMessage.fromJson(message.value(), InspectedPostMessagePayload::class.java, objectMapper)
        when (message) {
            is CdcMessage.Create ->
                postIndexingUseCase.save(message.payload.toModel())
            is CdcMessage.Update ->
                postIndexingUseCase.save(message.payload.toModel())
            is CdcMessage.Delete ->
                postIndexingUseCase.delete(PostId.from(message.id))
        }

    }
}