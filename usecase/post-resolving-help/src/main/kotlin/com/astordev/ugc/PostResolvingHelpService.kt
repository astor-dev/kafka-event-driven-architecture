package com.astordev.ugc

import com.astordev.ugc.port.MetadataPort
import com.astordev.ugc.port.PostPort
import com.astordev.ugc.post.model.Post
import com.astordev.ugc.post.model.PostId
import com.astordev.ugc.post.model.ResolvedPost

class PostResolvingHelpService(
    private val postPort: PostPort,
    private val metadataPort: MetadataPort
) : PostResolvingHelpUseCase {
    override fun resolvePostById(postId: PostId): ResolvedPost? {
        TODO("Not yet implemented")
    }

    override fun resolvePostsByIds(postIds: List<PostId>): List<ResolvedPost> {
        TODO("Not yet implemented")
    }

    override fun resolvePostAndSave(post: Post) {
        TODO("Not yet implemented")
    }

    override fun deleteResolvedPost(postId: PostId) {
        TODO("Not yet implemented")
    }
}