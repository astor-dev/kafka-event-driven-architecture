package com.astordev.ugc

import arrow.core.Either
import com.astordev.ugc.post.model.PostId
import com.astordev.ugc.post.model.ResolvedPost

interface PostReadUseCase {
    fun getById(postId: PostId): Either<PostResolvingError,ResolvedPost>
}