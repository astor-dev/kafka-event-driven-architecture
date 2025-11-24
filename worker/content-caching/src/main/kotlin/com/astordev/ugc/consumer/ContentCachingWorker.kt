package com.astordev.ugc.consumer

import com.astordev.ugc.PostResolvingHelpUseCase
import com.astordev.ugc.adapter.common.OperationType
import com.astordev.ugc.adapter.common.Topic
import com.astordev.ugc.adapter.originpost.OriginalPostMessage
import com.astordev.ugc.adapter.originpost.toModel
import com.astordev.ugc.post.model.PostId
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ContentCachingWorker(
    private val postResolvingHelpUseCase: PostResolvingHelpUseCase,
    private val objectMapper: ObjectMapper
) {


    @KafkaListener(
        topics = [Topic.ORIGINAL_POST ],
        groupId = "cache-post-consumer-group",
        concurrency = "3"
    )
    fun listen(message: ConsumerRecord<String, String>) {
        val message = objectMapper.readValue(message.value(), OriginalPostMessage::class.java)
        val payloadOrNull = message.toModel()
        when(message.operationType){
            OperationType.CREATE ->
                postResolvingHelpUseCase.resolvePostAndSave(payloadOrNull)
            OperationType.UPDATE ->
                postResolvingHelpUseCase.resolvePostAndSave(payloadOrNull)
            OperationType.DELETE ->
                postResolvingHelpUseCase.deleteResolvedPost(PostId.from(message.id))
        }


    }

}