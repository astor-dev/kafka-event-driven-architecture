package com.astordev.ugc.port

import com.astordev.ugc.inspectedpost.model.InspectedPost
import com.astordev.ugc.post.model.PostId

interface InspectedPostMessageProducePort {
    fun sendCreateMessage(inspectedPost: InspectedPost)
    fun sendUpdateMessage(inspectedPost: InspectedPost)
    fun sendDeleteMessage(postId: PostId)
}
