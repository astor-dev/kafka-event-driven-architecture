package com.astordev.ugc

import com.astordev.ugc.post.model.PostId
import com.astordev.ugc.post.model.ResolvedPost

interface PostReadUseCase {
    fun getById(postId: PostId): ResolvedPost?
}