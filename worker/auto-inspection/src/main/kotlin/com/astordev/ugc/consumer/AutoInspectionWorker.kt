package com.astordev.ugc.consumer

import com.astordev.ugc.PostInspectUseCase
import com.astordev.ugc.adapter.common.CdcMessage
import com.astordev.ugc.adapter.common.Topic
import com.astordev.ugc.adapter.originpost.OriginalPostMessagePayload
import com.astordev.ugc.adapter.originpost.toModel
import com.astordev.ugc.port.InspectedPostMessageProducePort
import com.astordev.ugc.post.model.PostId
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class AutoInspectionWorker(
    private val postInspectUseCase: PostInspectUseCase,
    private val inspectedMessageProducerPort: InspectedPostMessageProducePort,
    private val objectMapper: ObjectMapper
) {


    @KafkaListener(
        topics = [Topic.ORIGINAL_POST],
        groupId = "auto-inspection-consumer-group",
        concurrency = "3"
    )
    fun listen(message: ConsumerRecord<String, String>) {
        val originalPostMessage = CdcMessage.fromJson(message.value(), OriginalPostMessagePayload::class.java, objectMapper)
        when(originalPostMessage) {
            is CdcMessage.Create -> {
                val post = originalPostMessage.payload.toModel()
                postInspectUseCase.inspect(post).fold(
                    ifLeft = { inspectedMessageProducerPort.sendDeleteMessage(post.id) },
                    ifRight = { inspectedMessageProducerPort.sendCreateMessage(it) }
                )
            }
            is CdcMessage.Update -> {
                val post = originalPostMessage.payload.toModel()
                postInspectUseCase.inspect(post).fold(
                    ifLeft = { inspectedMessageProducerPort.sendDeleteMessage(post.id) },
                    ifRight = { inspectedMessageProducerPort.sendUpdateMessage(it) }
                )
            }
            is CdcMessage.Delete -> {
                inspectedMessageProducerPort.sendDeleteMessage(PostId.from(originalPostMessage.id))
            }
        }
    }
}