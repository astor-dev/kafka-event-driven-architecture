package com.astordev.ugc.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.apache.logging.log4j.message.Message


data class ChatCompletionResponse (
    val id: String,
    val `object`: String,
    val created : Long,
    val model: String,
    val choices: List<ChatChoice>,
    val usage: Usage,
    @param:JsonProperty("system_fingerprint")
    val systemFingerprint: String?
){
    data class ChatChoice (
        val index : Int,
        val message: Message,
        val logprobs: Any?,
        @param:JsonProperty("finish_reason")
        val finishReason: String? = null
    ) {
        data class Message (
            val role: String,
            val content: String
        )
    }
    data class Usage (
        @param:JsonProperty("prompt_tokens")
        val promptTokens: Int,
        @param:JsonProperty("completion_tokens")
        val completionTokens: Int,
        @param:JsonProperty("total_tokens")
        val totalTokens: Int
    )
}