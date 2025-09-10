package com.astordev.ugc

import com.astordev.ugc.post.model.PostId
import com.astordev.ugc.post.model.ResolvedPost
import org.springframework.stereotype.Service

@Service
class PostReadService : PostReadUseCase {
    override fun getById(postId: PostId): ResolvedPost? {
        TODO("Not yet implemented")
    }
}