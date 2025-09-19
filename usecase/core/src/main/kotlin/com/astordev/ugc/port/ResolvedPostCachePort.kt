package com.astordev.ugc.port

import com.astordev.ugc.post.model.PostId
import com.astordev.ugc.post.model.ResolvedPost

interface ResolvedPostCachePort {

    fun set(resolvedPost: ResolvedPost)
    fun get(postId: PostId): ResolvedPost?
}