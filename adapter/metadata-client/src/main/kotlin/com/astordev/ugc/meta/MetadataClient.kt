package com.astordev.ugc.meta

import com.astordev.ugc.category.model.CategoryId
import com.astordev.ugc.user.model.UserId
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class MetadataClient(
    private val webClient: WebClient
) {

    fun getCategoryById(categoryId: CategoryId): CategoryResponse? {
        return webClient
            .get()
            .uri("/categories/{categoryId}", categoryId.long)
            .retrieve()
            .bodyToMono(CategoryResponse::class.java)
            .block()
    }

    fun getUserById(userId: UserId): UserResponse? {
        return webClient
            .get()
            .uri("/users/{userId}", userId.long)
            .retrieve()
            .bodyToMono(UserResponse::class.java)
            .block()
    }


    fun getFollowerIdsByUserId(userId: UserId): List<Long> {
        return webClient
            .get()
            .uri("/followers?followingId={id}", userId.long)            .retrieve()
            .bodyToFlux(Long::class.java)
            .collectList()
            .block()
            ?: emptyList()
    }


    data class CategoryResponse(
        val id: Long,
        val name: String
    )

    data class UserResponse(
        val id: Long,
        val email: String,
        val name: String
    )
}