package com.astordev.ugc.config

import com.fasterxml.jackson.core.JsonProcessingException
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.*
import org.springframework.kafka.listener.*
import org.springframework.util.backoff.BackOff
import org.springframework.util.backoff.ExponentialBackOff
import java.util.concurrent.atomic.AtomicReference

@Configuration
@EnableKafka
class KafkaConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.kafka")
    fun kafkaProperties(): KafkaProperties = KafkaProperties()

    @Bean
    @Primary
    fun producerFactory(kafkaProperties: KafkaProperties): ProducerFactory<String, String> {
        return DefaultKafkaProducerFactory(
            mapOf<String, Any>(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProperties.bootstrapServers,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java.name,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java.name,
                ProducerConfig.ACKS_CONFIG to "-1",
                ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG to "true"
            )
        )
    }

    @Bean
    @Primary
    fun kafkaTemplate(kafkaProperties: KafkaProperties): KafkaTemplate<String, String> {
        return KafkaTemplate(producerFactory(kafkaProperties))
    }

    @Bean
    @Primary
    fun consumerFactory(kafkaProperties: KafkaProperties): ConsumerFactory<String, Any> {
        val props = mapOf<String, Any>(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProperties.bootstrapServers,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG to "false",
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to "false"
        )
        return DefaultKafkaConsumerFactory(props)
    }

    @Bean
    @Primary
    fun concurrentKafkaListenerFactory(
        consumerFactory: ConsumerFactory<String, Any>,
        errorHandler: CommonErrorHandler
    ): ConcurrentKafkaListenerContainerFactory<String, String> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = consumerFactory
        factory.setCommonErrorHandler(errorHandler)
        factory.containerProperties.ackMode = ContainerProperties.AckMode.RECORD
        return factory
    }

    @Bean
    @Primary
    fun errorHandler(): CommonErrorHandler {
        val commonContainerStoppingErrorHandler = CommonContainerStoppingErrorHandler()
        val consumer2: AtomicReference<Consumer<*, *>> = AtomicReference()
        val container2: AtomicReference<MessageListenerContainer> = AtomicReference()

        val errorHandler = object : DefaultErrorHandler({rec, ex ->
            commonContainerStoppingErrorHandler.handleRemaining(
                ex, listOf(rec), consumer2.get(), container2.get()
                )
        }, generateBackOff()) {
            override fun handleRemaining(
                thrownException: Exception,
                records: List<ConsumerRecord<*,*>>,
                consumer: Consumer<*,*>,
                container: MessageListenerContainer
            ) {
                consumer2.set(consumer)
                container2.set(container)
                super.handleRemaining(thrownException, records, consumer, container)
            }
        }
        errorHandler.addNotRetryableExceptions(JsonProcessingException::class.java)
        return errorHandler
    }

    fun generateBackOff(): BackOff {
        val backOff = ExponentialBackOff(1000, 2.0)
        backOff.maxAttempts = 3
        return backOff
    }
}