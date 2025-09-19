package com.astordev.ugc

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
    override fun resolvePostById(postId: PostId): ResolvedPost? {
        resolvedPostCachePort.get(postId)?.let {
            return it
        }
        postPort.findById(postId)?.let {
            this.resolvePost(it)?.let {
                resolvedPost -> return resolvedPost
            }
        }
        return null
    }

    override fun resolvePostsByIds(postIds: List<PostId>): List<ResolvedPost> {
        val posts = postPort.listByIds(postIds)
        return posts.mapNotNull {
            resolvePost(it)
        }
    }

    override fun resolvePostAndSave(post: Post) {
        resolvePost(post)?.let {
            resolvedPostCachePort.set(it)
        }
    }

    override fun deleteResolvedPost(postId: PostId) {
        resolvedPostCachePort.delete(postId)
    }

    private fun resolvePost(post: Post): ResolvedPost? {
        var resolvedPost: ResolvedPost? = null
        val userNameResult: Result<String, MetadataPort.GetUserError> = metadataPort.getUserNameByUserId(post.userId)
        val categoryNameResult: Result<String, MetadataPort.GetCategoryError> = metadataPort.getCategoryNameByCategoryId(post.categoryId)
        if (userNameResult is Result.Success && categoryNameResult is Result.Success) {
            resolvedPost = ResolvedPost.generate(
                post,
                userNameResult.data,
                categoryNameResult.data
            )
        }
        return resolvedPost
    }
}