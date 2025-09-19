package com.astordev.ugc.subscribingpost

import com.astordev.ugc.port.SubscribingPostPort
import com.astordev.ugc.post.model.Post
import com.astordev.ugc.post.model.PostId
import com.astordev.ugc.user.model.UserId
import org.springframework.stereotype.Component

@Component
class SubscribingPostAdapter(
    private val subscribingPostRepository: SubscribingPostRepository
): SubscribingPostPort {
    override fun addPostToFollowerInboxes(
        post: Post,
        followerUserIds: List<UserId>
    ) {
        val documents = followerUserIds.map { followerUserId ->
            SubscribingPostDocument.generate(post, followerUserId)
        }
        subscribingPostRepository.saveAll(documents)
    }

    override fun removePostFromFollowerInboxes(postId: PostId) {
        subscribingPostRepository.deleteAllByPostId(postId)
    }

    override fun listPostIdsByFollowerUserIdWithPagination(
        followerUserId: UserId,
        pageNumber: Int,
        pageSize: Int
    ): List<PostId> {
        val documents = subscribingPostRepository.findByFollowerUserIdWithPagination(followerUserId, pageNumber, pageSize)
        return documents.map { document -> PostId.from(document.postId) }
    }

}