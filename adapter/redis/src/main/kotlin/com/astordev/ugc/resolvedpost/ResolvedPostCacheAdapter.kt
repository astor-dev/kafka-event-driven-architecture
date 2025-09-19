package com.astordev.ugc.resolvedpost

import com.astordev.ugc.CustomObjectMapper
import com.astordev.ugc.port.ResolvedPostCachePort
import com.astordev.ugc.post.model.PostId
import com.astordev.ugc.post.model.ResolvedPost
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class ResolvedPostCacheAdapter(
    private val redisTemplate: RedisTemplate<String, String>
): ResolvedPostCachePort {
    companion object {
        private const val KEY_PREFIX = "resolved_post:v1:"
        private const val EXPIRE_SECONDS = 60L * 60L * 2L
    }

    private val objectMapper = CustomObjectMapper()
    override fun set(resolvedPost: ResolvedPost) {
        val jsonString = objectMapper.writeValueAsString(resolvedPost)
        return redisTemplate.opsForValue().set(
            generateCacheKey(resolvedPost.id),
            jsonString,
            EXPIRE_SECONDS
            )
    }

    override fun get(postId: PostId): ResolvedPost? {
        val jsonString = redisTemplate.opsForValue().get(generateCacheKey(postId))
            ?: return null
        val resolvedPost = runCatching {
            objectMapper.readValue(jsonString, ResolvedPost::class.java)
        }.getOrNull()
        return resolvedPost
    }


    private fun generateCacheKey(postId: PostId): String {
        return "$KEY_PREFIX$postId"
    }
}