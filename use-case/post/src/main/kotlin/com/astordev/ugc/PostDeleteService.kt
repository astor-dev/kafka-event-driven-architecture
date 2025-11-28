package com.astordev.ugc

import arrow.core.Either
import arrow.core.raise.context.either
import arrow.core.raise.context.raise
import com.astordev.ugc.port.OriginPostMessageProducePort
import com.astordev.ugc.port.PostPort
import com.astordev.ugc.post.model.Post
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class PostDeleteService(
    private val postPort: PostPort,
    private val originPostMessageProducePort: OriginPostMessageProducePort
) : PostDeleteUseCase {

    @Transactional
    override fun delete(request: PostDeleteUseCase.Request): Either<PostDeleteError, Post> = either {
        val post = postPort.findById(request.postId)
            ?: raise(PostDeleteError.PostNotFound(request.postId))
        post.delete()
        val savedPost = postPort.save(post)
        originPostMessageProducePort.sendDeleteMessage(savedPost)
        savedPost
    }
}