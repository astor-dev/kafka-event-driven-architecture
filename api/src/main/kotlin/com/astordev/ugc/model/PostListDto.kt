package com.astordev.ugc.model

import com.astordev.ugc.post.model.ResolvedPost
import java.time.LocalDateTime


data class PostListDto (
    val id: String,
    val title: String,
    val userName: String,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(post: ResolvedPost): PostListDto {
            return PostListDto(
                post.id.long.toString(),
                post.title,
                post.userName,
                post.createdAt,
            )
        }
    }
}