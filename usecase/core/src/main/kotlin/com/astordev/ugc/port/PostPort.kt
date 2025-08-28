package com.astordev.ugc.port

import com.astordev.ugc.post.model.Post
import com.astordev.ugc.post.model.PostId

interface PostPort {
    fun save(post: Post): Post
    fun findById(id: PostId): Post?
    fun listByIds(ids: List<PostId>): List<Post>
}