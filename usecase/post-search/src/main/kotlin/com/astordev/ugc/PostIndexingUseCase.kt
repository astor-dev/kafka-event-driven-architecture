package com.astordev.ugc

import com.astordev.ugc.inspectedpost.model.InspectedPost
import com.astordev.ugc.post.model.PostId

interface PostIndexingUseCase {
    fun save(post: InspectedPost)
    fun delete(postId: PostId)
}