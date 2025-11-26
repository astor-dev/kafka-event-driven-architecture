package com.astordev.ugc

import arrow.core.Either
import com.astordev.ugc.post.error.PostResolvingError
import com.astordev.ugc.post.model.Post
import com.astordev.ugc.post.model.PostId
import com.astordev.ugc.post.model.ResolvedPost

interface PostResolvingHelpUseCase {
    fun resolvePostById(postId: PostId): Either<PostResolvingError, ResolvedPost>
    fun resolvePostsByIds(postIds: List<PostId>): Either<PostResolvingError, List<ResolvedPost>>
    fun resolvePostAndSave(post: Post)
    fun deleteResolvedPost(postId: PostId)
}