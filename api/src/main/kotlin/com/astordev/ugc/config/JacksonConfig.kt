package com.astordev.ugc.config

import com.astordev.ugc.utils.ObjectMapperUtils
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfig {
    @Bean
    fun objectMapperCustomizer(): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer { builder ->
            builder.modulesToInstall(
                *ObjectMapperUtils.getCommonJacksonModules().toTypedArray()
            )
        }
    }
}