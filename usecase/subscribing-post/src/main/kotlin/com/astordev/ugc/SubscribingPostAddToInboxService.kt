package com.astordev.ugc

import com.astordev.ugc.port.MetadataPort
import com.astordev.ugc.port.SubscribingPostPort
import com.astordev.ugc.post.model.Post
import org.springframework.stereotype.Service

@Service
class SubscribingPostAddToInboxService(
    private val subscribingPostPort: SubscribingPostPort,
    private val metadataPort: MetadataPort
) : SubscribingPostAddToInboxUseCase {
    override fun saveSubscribingInboxPost(post: Post) {
        val followerIds = metadataPort.listFollowerIdsByUserId(post.userId)
        subscribingPostPort.addPostToFollowerInboxes(post, followerIds)
    }
}