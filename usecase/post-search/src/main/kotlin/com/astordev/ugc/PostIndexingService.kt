package com.astordev.ugc

import com.astordev.ugc.inspectedpost.model.InspectedPost
import com.astordev.ugc.port.PostSearchPort
import com.astordev.ugc.post.model.PostId
import org.springframework.stereotype.Component

@Component
class PostIndexingService(
    private val postSearchPort: PostSearchPort
) : PostIndexingUseCase {
    override fun save(post: InspectedPost) {
        postSearchPort.indexPost(post)
    }

    override fun delete(postId: PostId) {
        postSearchPort.deletePost(postId)
    }
}