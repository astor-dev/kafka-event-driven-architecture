package com.astordev.ugc.post.model

import com.astordev.ugc.IdProvider
import com.astordev.ugc.SnowflakeIdFactory

@JvmInline
value class PostId private constructor(val long: Long) {
    companion object : IdProvider<PostId> by SnowflakeIdFactory(::PostId)
}