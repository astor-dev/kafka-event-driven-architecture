package com.astordev.ugc.post.model

import com.astordev.ugc.SnowflakeIdFactory
import com.astordev.ugc.IdProvider

@JvmInline
value class PostId private constructor(val id: Long) {
    companion object : IdProvider<PostId> by SnowflakeIdFactory(::PostId)
};