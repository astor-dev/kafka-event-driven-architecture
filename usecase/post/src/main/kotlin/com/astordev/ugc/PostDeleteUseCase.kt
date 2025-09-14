package com.astordev.ugc

import com.astordev.ugc.post.model.Post
import com.astordev.ugc.post.model.PostId

interface PostDeleteUseCase {
    fun delete(request: Request): Result<Post, Error>

    data class Request (
        val postId: PostId,
    )

    sealed class Error {
        object PostNotFound: Error()
    }
}