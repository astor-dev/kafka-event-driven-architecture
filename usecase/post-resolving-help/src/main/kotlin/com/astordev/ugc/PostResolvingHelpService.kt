package com.astordev.ugc

import com.astordev.ugc.post.model.Post
import com.astordev.ugc.post.model.PostId
import com.astordev.ugc.post.model.ResolvedPost

class PostResolvingHelpService : PostResolvingHelpUsecase {
    override fun resolvePostById(postId: PostId): ResolvedPost {
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