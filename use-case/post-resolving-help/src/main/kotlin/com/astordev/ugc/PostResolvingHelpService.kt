package com.astordev.ugc

import arrow.core.Either
import arrow.core.raise.context.bind
import arrow.core.raise.context.either
import arrow.core.raise.context.raise
import com.astordev.ugc.port.MetadataPort
import com.astordev.ugc.port.PostPort
import com.astordev.ugc.port.ResolvedPostCachePort
import com.astordev.ugc.post.model.Post
import com.astordev.ugc.post.model.PostId
import com.astordev.ugc.post.model.ResolvedPost
import org.springframework.stereotype.Service


@Service
class PostResolvingHelpService(
    private val postPort: PostPort,
    private val metadataPort: MetadataPort,
    private val resolvedPostCachePort: ResolvedPostCachePort
) : PostResolvingHelpUseCase {
    override fun resolvePostById(postId: PostId): Either<PostResolvingError, ResolvedPost> = either {
        resolvedPostCachePort.get(postId)?.let { return@either it }

        val post = postPort.findById(postId) ?: raise(PostResolvingError.PostNotFound(postId))
        return this.resolvePost(post)
    }

    override fun resolvePostsByIds(postIds: List<PostId>): Either<PostResolvingError, List<ResolvedPost>> = either {
        val cachedPosts = resolvedPostCachePort.multiGet(postIds)
        val cachedIds = cachedPosts.map { it.id }.toSet()
        val missingIds = postIds.filter { it !in cachedIds }

        val freshPosts = postPort.listByIds(missingIds).map {
            resolvePost(it).bind()
        }
        val allResolvedPostsById = (freshPosts + cachedPosts).associateBy { it.id }
        postIds.mapNotNull { allResolvedPostsById[it] }
    }

    override fun resolvePostAndSave(post: Post) {
        resolvePost(post).onRight { resolvedPost ->
            resolvedPostCachePort.set(resolvedPost)
        }
    }

    override fun deleteResolvedPost(postId: PostId) {
        resolvedPostCachePort.delete(postId)
    }

    private fun resolvePost(post: Post): Either<PostResolvingError, ResolvedPost> = either {
        val userName = metadataPort.getUserNameByUserId(post.userId) ?: raise(PostResolvingError.UserNotFound(post.userId))
        val categoryName = metadataPort.getCategoryNameByCategoryId(post.categoryId) ?: raise((PostResolvingError.CategoryNotFound(post.categoryId)))

        ResolvedPost.generate(
            post,
            userName,
            categoryName
        )
    }
}