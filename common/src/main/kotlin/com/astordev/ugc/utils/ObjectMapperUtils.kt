package com.astordev.ugc.utils

import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule

object ObjectMapperUtils {

    fun getCommonJacksonModules(): List<Module> {
        return listOf(
            JavaTimeModule(), // Java 8 날짜/시간 (JSR-310) 지원
            KotlinModule.Builder().build() // Kotlin 지원
        )
    }

    fun createCommonObjectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        getCommonJacksonModules().forEach { module ->
            objectMapper.registerModule(module)
        }
        // 필요하다면 공통 설정을 여기에 추가 가능
        // objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        return objectMapper
    }
}