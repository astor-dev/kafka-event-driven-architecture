package com.astordev.ugc

import com.astordev.ugc.port.OriginPostMessageProducePort
import com.astordev.ugc.port.PostPort
import com.astordev.ugc.post.model.Post
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service


@Service
class PostCreateService(
    private val postPort: PostPort,
    private val originPostMessageProducePort: OriginPostMessageProducePort
) : PostCreateUseCase {

    @Transactional
    override fun create(request: PostCreateUseCase.Request): Post {
        val post = Post.generate(request.userId, request.title,request.content, request.categoryId)
        val savedPost = postPort.save(post)
        originPostMessageProducePort.sendCreateMessage(post)
        return savedPost
    }
}