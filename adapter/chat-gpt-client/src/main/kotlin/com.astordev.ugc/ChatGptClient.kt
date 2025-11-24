package com.astordev.ugc

import com.astordev.ugc.model.ChatCompletionResponse
import com.astordev.ugc.utils.ObjectMapperUtils
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class ChatGptClient(
    @param:Qualifier("chatGptWebClient")
    private val chatGptWebClient: WebClient,
    // NOTE: 특정 설정이 필요해서 Bean 사용 X
    private val objectMapper: ObjectMapper = ObjectMapperUtils.createCommonObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

) {
    private val TARGET_GPT_MODEL = "gpt-3.5-turbo"

    @Value("\${openai.api_key}")
    private lateinit var openaiApiKey: String



    fun getResultForContentWithPolicy(
        content: String,
        chatPolicy: ChatPolicy
    ): String {
        val requestBody = mapOf(
            "model" to TARGET_GPT_MODEL,
            "messages" to listOf(
                mapOf("role" to "system", "content" to chatPolicy.instruction),
                mapOf("role" to "user", "content" to chatPolicy.exampleContent),
                mapOf("role" to "assistant", "content" to chatPolicy.exampleInspectionResult),
                mapOf("role" to "user", "content" to content)
            ),
            "stream" to false
        )

        val jsonString: String = chatGptWebClient
            .post()
            .uri("/v1/chat/completions")
            .header("Authorization", "Bearer $openaiApiKey")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(String::class.java)
            .block() ?: throw RuntimeException("응답이 비어있습니다.")

        return try {
            val response = objectMapper.readValue(jsonString, ChatCompletionResponse::class.java)
            response.choices.getOrNull(0)?.message?.content
                ?: throw RuntimeException("응답에서 콘텐츠를 찾을 수 없습니다.")
        } catch (e: Exception) {
            print(jsonString)
            throw RuntimeException(e)
        }
    }

    data class ChatPolicy(
        val instruction: String,
        val exampleContent: String,
        val exampleInspectionResult: String
    )
}