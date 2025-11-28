package com.astordev.ugc

import arrow.core.Either
import arrow.core.raise.either
import com.astordev.ugc.port.OriginPostMessageProducePort
import com.astordev.ugc.port.PostPort
import com.astordev.ugc.post.model.Post
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class PostUpdateService (
    private val postPort: PostPort,
    private val originPostMessageProducePort: OriginPostMessageProducePort,
) : PostUpdateUseCase {

    @Transactional
    override fun update(request: PostUpdateUseCase.Request): Either<PostUpdateError, Post>  = either {
        val post = postPort.findById(request.postId)
            ?: raise(PostUpdateError.PostNotFound)
        post.update(
            request.title,
            request.content,
            request.categoryId
        )
        val savedPost = postPort.save(post)
        originPostMessageProducePort.sendUpdateMessage(savedPost)
        savedPost
    }
}