package com.astordev.ugc.model

import com.astordev.ugc.post.model.Post
import com.astordev.ugc.post.model.ResolvedPost
import java.time.LocalDateTime


data class PostListDto (
    val id: Long,
    val title: String,
    val userName: String,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(post: ResolvedPost): PostListDto {
            return PostListDto(
                post.id.long,
                post.title,
                post.userName,
                post.createdAt,
            )
        }
    }
}