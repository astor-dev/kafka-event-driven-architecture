package com.astordev.ugc

import com.astordev.ugc.post.model.ResolvedPost
import com.astordev.ugc.user.model.UserId

interface SubscribingPostListUseCase {

    fun listSubscribingInboxPosts(request: Request) : List<ResolvedPost>

    data class Request (
        val pageNumber: Int,
        val followerUserId: UserId
    )
}