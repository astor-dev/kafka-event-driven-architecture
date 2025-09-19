package com.astordev.ugc

import com.astordev.ugc.post.model.Post
import com.astordev.ugc.post.model.ResolvedPost
import com.astordev.ugc.user.model.UserId

interface SubscribingPostAddToInboxUseCase {
    fun saveSubscribingInboxPost(post: Post)
}