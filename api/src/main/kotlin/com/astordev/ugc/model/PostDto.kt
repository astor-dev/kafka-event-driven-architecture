package com.astordev.ugc.model

import com.astordev.ugc.post.model.Post
import java.time.LocalDateTime


data class PostDto (
    val id: Long,
    val title: String,
    val content: String,
    val userId: Long,
    val categoryId: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val deletedAt: LocalDateTime?
) {
    companion object {
        fun from(post: Post): PostDto {
            return PostDto(
                post.id.long,
                post.title,
                post.content,
                post.userId.long,
                post.categoryId.long,
                post.createdAt,
                post.updatedAt,
                post.deletedAt
            )
        }
    }
}