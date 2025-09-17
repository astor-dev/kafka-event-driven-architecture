package com.astordev.ugc

import com.astordev.ugc.port.MetadataPort
import com.astordev.ugc.port.PostPort
import com.astordev.ugc.post.model.Post
import com.astordev.ugc.post.model.PostId
import com.astordev.ugc.post.model.ResolvedPost
import org.springframework.stereotype.Service


@Service
class PostResolvingHelpService(
    private val postPort: PostPort,
    private val metadataPort: MetadataPort
) : PostResolvingHelpUseCase {
    override fun resolvePostById(postId: PostId): ResolvedPost? {
        val post = postPort.findById(postId)
        var resolvedPost: ResolvedPost? = null
        if (post != null) {
            resolvedPost = this.resolvePost(post)
        }
        return resolvedPost
    }

    override fun resolvePostsByIds(postIds: List<PostId>): List<ResolvedPost> {
        val posts = postPort.listByIds(postIds)
        return posts.mapNotNull {
            resolvePost(it)
        }
    }

    override fun resolvePostAndSave(post: Post) {
        TODO("Not yet implemented")
    }

    override fun deleteResolvedPost(postId: PostId) {
        TODO("Not yet implemented")
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