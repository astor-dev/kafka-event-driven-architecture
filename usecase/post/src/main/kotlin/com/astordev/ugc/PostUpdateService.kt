package com.astordev.ugc

import com.astordev.ugc.port.PostPort
import com.astordev.ugc.post.model.Post
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class PostUpdateService (
    private val postPort: PostPort
) : PostUpdateUseCase {

    @Transactional
    override fun update(request: PostUpdateUseCase.Request): Result<Post, PostUpdateUseCase.Error> {
        val post = postPort.findById(request.postId)
            ?: return Result.Failure(PostUpdateUseCase.Error.PostNotFound)
        post.update(
            request.title,
            request.content,
            request.categoryId
        )
        val savedPost = postPort.save(post)
        return Result.Success(savedPost)
    }
}