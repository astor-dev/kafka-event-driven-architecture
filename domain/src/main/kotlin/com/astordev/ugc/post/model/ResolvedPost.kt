package com.astordev.ugc.post.model

import com.astordev.ugc.category.model.CategoryId
import com.astordev.ugc.user.model.UserId
import java.time.LocalDateTime



data class ResolvedPost (
    val id: PostId,
    val title: String,
    val content: String,
    val userId: UserId,
    val userName: String,
    val categoryId: CategoryId,
    val categoryName: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val updated: Boolean
) {

    companion object {
        fun generate(
            post: Post,
            userName: String,
            categoryName: String
        ): ResolvedPost {
            return ResolvedPost(
                post.id,
                post.title,
                post.content,
                post.userId,
                userName,
                post.categoryId,
                categoryName,
                post.createdAt,
                post.updatedAt,
                !post.createdAt.equals(post.updatedAt)
            )
        }
    }
}