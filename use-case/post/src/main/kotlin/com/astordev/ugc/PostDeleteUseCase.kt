package com.astordev.ugc

import arrow.core.Either
import com.astordev.ugc.post.model.Post
import com.astordev.ugc.post.model.PostId

interface PostDeleteUseCase {
    fun delete(request: Request): Either<PostDeleteError, Post>

    data class Request (
        val postId: PostId,
    )
}

sealed interface PostDeleteError : DomainError {
    object PostNotFound : PostDeleteError
}