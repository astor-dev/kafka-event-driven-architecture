package com.astordev.ugc

import com.astordev.ugc.post.model.PostId

interface SubscribingPostRemoveFromInboxUseCase {
    fun deleteSubscribingInboxPost(postId: PostId)
}