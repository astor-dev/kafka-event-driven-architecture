package com.astordev.ugc

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
    override fun delete(request: PostDeleteUseCase.Request): Result<Post, PostDeleteUseCase.Error> {
        val post = postPort.findById(request.postId)
            ?: return Result.Failure(PostDeleteUseCase.Error.PostNotFound)
        post.delete()
        val savedPost = postPort.save(post)
        originPostMessageProducePort.sendDeleteMessage(savedPost.id)
        return Result.Success(savedPost)
    }
}