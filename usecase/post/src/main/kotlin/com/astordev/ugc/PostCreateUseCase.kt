package com.astordev.ugc

import com.astordev.ugc.category.model.CategoryId
import com.astordev.ugc.post.model.Post
import com.astordev.ugc.user.model.UserId

interface PostCreateUseCase {
    fun create(request: Request): Post

    data class Request (
        val userId: UserId,
        val title: String,
        val content: String,
        val categoryId: CategoryId
    )
}