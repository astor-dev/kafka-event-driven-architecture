package com.astordev.ugc

import com.astordev.ugc.post.model.Post
import com.astordev.ugc.post.model.PostId
import com.astordev.ugc.post.model.ResolvedPost

interface PostResolvingHelpUsecase {
    fun resolvePostById(postId: PostId): ResolvedPost
    fun resolvePostsByIds(postIds: List<PostId>): List<ResolvedPost>
    fun resolvePostAndSave(post: Post)
    fun deleteResolvedPost(postId: PostId);
}