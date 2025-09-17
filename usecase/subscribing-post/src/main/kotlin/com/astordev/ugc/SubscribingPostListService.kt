package com.astordev.ugc

import com.astordev.ugc.port.SubscribingPostPort
import com.astordev.ugc.post.model.ResolvedPost
import org.springframework.stereotype.Service

@Service
class SubscribingPostListService(
    private val subscribingPostPort: SubscribingPostPort,
    private val postResolvingHelpUseCase: PostResolvingHelpUseCase
) : SubscribingPostListUseCase {

    companion object {
        private const val PAGE_SIZE = 5
    }

    override fun listSubscribingInboxPosts(request: SubscribingPostListUseCase.Request): List<ResolvedPost> {
        val subscribingPostIds = subscribingPostPort.listPostIdsByFollowerUserIdWithPagination(
            request.followerUserId,
            request.pageNumber,
            PAGE_SIZE
        )
        return postResolvingHelpUseCase.resolvePostsByIds(subscribingPostIds)
    }
}