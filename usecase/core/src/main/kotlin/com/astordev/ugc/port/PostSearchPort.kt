package com.astordev.ugc.port

import com.astordev.ugc.inspectedpost.model.InspectedPost
import com.astordev.ugc.post.model.PostId

interface PostSearchPort {
    fun indexPost(post: InspectedPost)
    fun deletePost(postId: PostId)
}