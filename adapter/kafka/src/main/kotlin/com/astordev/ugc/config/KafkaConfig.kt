package com.astordev.ugc.config

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
@EnableKafka
@ConfigurationProperties("spring.kafka")
class KafkaConfig {

    @Bean
    @Primary
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
}