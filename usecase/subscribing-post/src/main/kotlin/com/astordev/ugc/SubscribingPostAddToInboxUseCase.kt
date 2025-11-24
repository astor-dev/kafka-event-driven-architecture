package com.astordev.ugc

import com.astordev.ugc.post.model.Post

interface SubscribingPostAddToInboxUseCase {
    fun saveSubscribingInboxPost(post: Post)
}