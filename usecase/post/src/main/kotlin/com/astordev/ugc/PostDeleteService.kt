package com.astordev.ugc

import com.astordev.ugc.post.model.Post
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class PostDeleteService : PostDeleteUseCase {

    @Transactional
    override fun delete(request: PostDeleteUseCase.Request): Post {
        TODO("Not yet implemented")
    }
}