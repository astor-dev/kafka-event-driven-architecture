package com.astordev.ugc.consumer

import com.astordev.ugc.PostResolvingHelpUseCase
import com.astordev.ugc.adapter.common.CdcMessage
import com.astordev.ugc.adapter.common.Topic
import com.astordev.ugc.adapter.originpost.OriginalPostMessagePayload
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
        val originalPostMessage = CdcMessage.fromJson(message.value(), OriginalPostMessagePayload::class.java, objectMapper)
        when(originalPostMessage){
            is CdcMessage.Create ->
                postResolvingHelpUseCase.resolvePostAndSave(originalPostMessage.payload.toModel())
            is CdcMessage.Update ->
                postResolvingHelpUseCase.resolvePostAndSave(originalPostMessage.payload.toModel())
            is CdcMessage.Delete ->
                postResolvingHelpUseCase.deleteResolvedPost(PostId.from(originalPostMessage.id))
        }


    }

}