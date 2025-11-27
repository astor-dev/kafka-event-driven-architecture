package com.astordev.ugc

import arrow.core.Either
import com.astordev.ugc.post.error.PostResolvingError
import com.astordev.ugc.post.model.ResolvedPost
import com.astordev.ugc.user.model.UserId

interface SubscribingPostListUseCase {

    fun listSubscribingInboxPosts(request: Request) : Either<PostResolvingError, List<ResolvedPost>>

    data class Request (
        val pageNumber: Int,
        val followerUserId: UserId
    )
}