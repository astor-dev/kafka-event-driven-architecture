package com.astordev.ugc

import com.astordev.ugc.port.SubscribingPostPort
import com.astordev.ugc.post.model.PostId
import org.springframework.stereotype.Service

@Service
class SubscribingPostRemoveFromInboxService(
    private val subscribingPostPort: SubscribingPostPort
) : SubscribingPostRemoveFromInboxUseCase {
    override fun deleteSubscribingInboxPost(postId: PostId) {
        subscribingPostPort.removePostFromFollowerInboxes(postId)
    }
}