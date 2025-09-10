package com.astordev.ugc.model

import com.astordev.ugc.post.model.Post
import java.time.LocalDateTime


data class PostDto (
    val id: String,
    val title: String,
    val content: String,
    val userId: String,
    val categoryId: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val deletedAt: LocalDateTime?
) {
    companion object {
        fun from(post: Post): PostDto {
            return PostDto(
                post.id.long.toString(),
                post.title,
                post.content,
                post.userId.long.toString(),
                post.categoryId.long.toString(),
                post.createdAt,
                post.updatedAt,
                post.deletedAt
            )
        }
    }
}