package com.astordev.ugc.consumer

import com.astordev.ugc.PostInspectUseCase
import com.astordev.ugc.Result
import com.astordev.ugc.adapter.common.OperationType
import com.astordev.ugc.adapter.common.Topic
import com.astordev.ugc.adapter.originpost.OriginalPostMessage
import com.astordev.ugc.adapter.originpost.toModel
import com.astordev.ugc.port.InspectedPostMessageProducePort
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
        val originalPostMessage = objectMapper.readValue(
            message.value(), OriginalPostMessage::class.java
        )
        val post = originalPostMessage.toModel()
        when(originalPostMessage.operationType) {
            OperationType.CREATE -> {
                when(val inspectionResult = postInspectUseCase.inspect(post)) {
                    is Result.Failure -> {
                        inspectedMessageProducerPort.sendDeleteMessage(post.id)
                    }
                    is Result.Success -> {
                        inspectedMessageProducerPort.sendCreateMessage(inspectionResult.data)
                    }
                }
            }
            OperationType.UPDATE -> {
                when(val inspectionResult = postInspectUseCase.inspect(post)) {
                    is Result.Failure -> {
                        inspectedMessageProducerPort.sendDeleteMessage(post.id)
                    }
                    is Result.Success -> {
                        inspectedMessageProducerPort.sendUpdateMessage(inspectionResult.data)
                    }
                }
            }
            OperationType.DELETE -> {
                inspectedMessageProducerPort.sendDeleteMessage(post.id)
            }
        }
    }
}