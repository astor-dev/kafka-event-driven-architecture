package com.astordev.ugc

import com.astordev.ugc.post.model.Post
import com.astordev.ugc.post.model.PostId

interface PostDeleteUseCase {
    fun delete(request: Request): Post

    data class Request (
        val postId: PostId,
    )
}