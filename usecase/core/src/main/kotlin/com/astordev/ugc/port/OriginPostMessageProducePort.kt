package com.astordev.ugc.port

import com.astordev.ugc.post.model.Post
import com.astordev.ugc.post.model.PostId

interface OriginPostMessageProducePort {
    fun sendCreateMessage(post: Post)
    fun sendUpdateMessage(post: Post)
    fun sendDeleteMessage(post: Post)
}