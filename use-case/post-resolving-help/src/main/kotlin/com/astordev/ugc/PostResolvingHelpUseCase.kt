package com.astordev.ugc

import arrow.core.Either
import com.astordev.ugc.category.model.CategoryId
import com.astordev.ugc.post.model.Post
import com.astordev.ugc.post.model.PostId
import com.astordev.ugc.post.model.ResolvedPost
import com.astordev.ugc.user.model.UserId

interface PostResolvingHelpUseCase {
    fun resolvePostById(postId: PostId): Either<PostResolvingError, ResolvedPost>
    fun resolvePostsByIds(postIds: List<PostId>): Either<PostResolvingError, List<ResolvedPost>>
    fun resolvePostAndSave(post: Post)
    fun deleteResolvedPost(postId: PostId)
}

sealed interface PostResolvingError : DomainError {
    data class UserNotFound(val id: UserId) : PostResolvingError
    data class CategoryNotFound(val id: CategoryId) : PostResolvingError
    data class PostNotFound(val id: PostId) : PostResolvingError
}