package com.astordev.ugc.consumer

import com.astordev.ugc.CustomObjectMapper
import com.astordev.ugc.PostResolvingHelpUseCase
import com.astordev.ugc.adapter.common.OperationType
import com.astordev.ugc.adapter.common.Topic
import com.astordev.ugc.adapter.originpost.OriginalPostConverter
import com.astordev.ugc.adapter.originpost.OriginalPostMessage
import com.astordev.ugc.post.model.PostId
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ContentCachingWorker(
    private val postResolvingHelpUseCase: PostResolvingHelpUseCase
) {

    private val objectMapper = CustomObjectMapper()

    @KafkaListener(
        topics = [Topic.ORIGINAL_POST ],
        groupId = "cache-post-consumer-group",
        concurrency = "3"
    )
    fun listen(message: ConsumerRecord<String, String>) {
        val message = objectMapper.readValue(message.value(), OriginalPostMessage::class.java)
        val payloadOrNull = OriginalPostConverter.toModel(message)
        when(message.operationType){
            OperationType.CREATE ->
                payloadOrNull?.let {
                    postResolvingHelpUseCase.resolvePostAndSave(payloadOrNull)
                }
            OperationType.UPDATE ->
                payloadOrNull?.let {
                    postResolvingHelpUseCase.resolvePostAndSave(payloadOrNull)
                }

            OperationType.DELETE ->
                postResolvingHelpUseCase.deleteResolvedPost(PostId.from(message.id))
        }


    }

}