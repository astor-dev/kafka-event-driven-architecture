package com.astordev.ugc.model

import com.astordev.ugc.post.model.ResolvedPost
import java.time.LocalDateTime


data class PostDetailDto (
    val id: String,
    val title: String,
    val content: String,
    val userName: String,
    val categoryName: String,
    val createdAt: LocalDateTime,
    val updated: Boolean
) {
    companion object {
        fun from(post: ResolvedPost): PostDetailDto {
            return PostDetailDto(
                post.id.long.toString(),
                post.title,
                post.content,
                post.userName,
                post.categoryName,
                post.createdAt,
                post.updated,
            )
        }
    }
}