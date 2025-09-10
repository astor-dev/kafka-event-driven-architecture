package com.astordev.ugc

import com.astordev.ugc.category.model.CategoryId
import com.astordev.ugc.post.model.Post
import com.astordev.ugc.post.model.PostId

interface PostUpdateUseCase {
    fun update(request: Request): Result<Post,Error>

    data class Request (
        val postId: PostId,
        val title: String,
        val content: String,
        val categoryId: CategoryId
    )

    sealed class Error {
        object PostNotFound: Error()
    }
}