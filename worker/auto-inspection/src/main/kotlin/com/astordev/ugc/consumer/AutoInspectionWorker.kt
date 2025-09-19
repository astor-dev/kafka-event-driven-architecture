package com.astordev.ugc.consumer

import com.astordev.ugc.CustomObjectMapper
import com.astordev.ugc.PostInspectUseCase
import com.astordev.ugc.Result
import com.astordev.ugc.adapter.common.OperationType
import com.astordev.ugc.adapter.common.Topic
import com.astordev.ugc.adapter.originpost.OriginalPostConverter
import com.astordev.ugc.adapter.originpost.OriginalPostMessage
import com.astordev.ugc.port.InspectedPostMessageProducePort
import com.astordev.ugc.post.model.PostId
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class AutoInspectionWorker(
    val postInspectUseCase: PostInspectUseCase,
    val inspectedMessageProducerPort: InspectedPostMessageProducePort
) {
    private val objectMapper = CustomObjectMapper()


    @KafkaListener(
        topics = [Topic.ORIGINAL_POST],
        groupId = "auto-inspection-consumer-group",
        concurrency = "3"
    )
    fun listen(message: ConsumerRecord<String, String>) {
        val originalPostMessage = objectMapper.readValue(
            message.value(), OriginalPostMessage::class.java
        )
        val postOrNull = OriginalPostConverter.toModel(originalPostMessage)
        println("일단 받긴 함 $postOrNull")
        println("ID 오버플로우 체크 ${originalPostMessage.id}")
        when(originalPostMessage.operationType) {
            OperationType.CREATE -> {
                postOrNull?.let {
                    when(val inspectionResult = postInspectUseCase.inspect(postOrNull)) {
                        is Result.Failure -> {
                            inspectedMessageProducerPort.sendDeleteMessage(postOrNull.id)
                        }
                        is Result.Success -> {
                            inspectedMessageProducerPort.sendCreateMessage(inspectionResult.data)
                        }
                    }
                }
            }
            OperationType.UPDATE -> {
                postOrNull?.let {
                    when(val inspectionResult = postInspectUseCase.inspect(postOrNull)) {
                        is Result.Failure -> {
                            inspectedMessageProducerPort.sendDeleteMessage(postOrNull.id)
                        }
                        is Result.Success -> {
                            inspectedMessageProducerPort.sendUpdateMessage(inspectionResult.data)
                        }
                    }
                }
            }
            OperationType.DELETE -> {
                inspectedMessageProducerPort.sendDeleteMessage(PostId.from(originalPostMessage.id))
            }
        }

    }
}