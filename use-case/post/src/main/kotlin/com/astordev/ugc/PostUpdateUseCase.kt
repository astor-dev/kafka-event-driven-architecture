package com.astordev.ugc

import arrow.core.Either
import com.astordev.ugc.category.model.CategoryId
import com.astordev.ugc.post.model.Post
import com.astordev.ugc.post.model.PostId

interface PostUpdateUseCase {
    fun update(request: Request): Either<PostUpdateError, Post>

    data class Request (
        val postId: PostId,
        val title: String,
        val content: String,
        val categoryId: CategoryId
    )
}

sealed interface PostUpdateError : DomainError {
    object PostNotFound : PostUpdateError
}