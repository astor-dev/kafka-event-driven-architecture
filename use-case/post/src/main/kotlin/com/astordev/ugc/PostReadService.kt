package com.astordev.ugc

import arrow.core.Either
import com.astordev.ugc.post.model.PostId
import com.astordev.ugc.post.model.ResolvedPost
import org.springframework.stereotype.Service

@Service
class PostReadService(
    private val postResolvingHelpUseCase: PostResolvingHelpUseCase
) : PostReadUseCase {
    override fun getById(postId: PostId): Either<PostResolvingError, ResolvedPost> {
        return postResolvingHelpUseCase.resolvePostById(postId)
    }
}