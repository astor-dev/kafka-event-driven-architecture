package com.astordev.ugc.postsearch

import com.astordev.ugc.inspectedpost.model.InspectedPost
import com.astordev.ugc.port.PostSearchPort
import com.astordev.ugc.post.model.PostId
import org.springframework.stereotype.Component

@Component
class PostSearchAdapter(
    private val postSearchRepository: PostSearchRepository
) : PostSearchPort {
    override fun indexPost(post: InspectedPost) {
        postSearchRepository.save(post.toDocument())
    }

    override fun deletePost(postId: PostId) {
        postSearchRepository.deleteById(postId.long)
    }
}