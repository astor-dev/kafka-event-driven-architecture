package com.astordev.ugc.consumer

import com.astordev.ugc.PostIndexingUseCase
import com.astordev.ugc.adapter.common.OperationType
import com.astordev.ugc.adapter.common.Topic
import com.astordev.ugc.adapter.inspectedpost.InspectedPostMessage
import com.astordev.ugc.adapter.inspectedpost.toModel
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
        val message : InspectedPostMessage = objectMapper.readValue(message.value(), InspectedPostMessage::class.java )
        val inspectedPost = message.toModel()
        when (message.operationType) {
            OperationType.CREATE ->
                postIndexingUseCase.save(inspectedPost)
            OperationType.UPDATE ->
                postIndexingUseCase.save(inspectedPost)
            OperationType.DELETE ->
                postIndexingUseCase.delete(inspectedPost.post.id)
        }

    }
}