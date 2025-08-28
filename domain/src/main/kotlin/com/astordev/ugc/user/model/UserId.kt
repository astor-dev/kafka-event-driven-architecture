package com.astordev.ugc.user.model

import com.astordev.ugc.IdProvider
import com.astordev.ugc.SnowflakeIdFactory

@JvmInline
value class UserId private constructor(val long: Long){
    companion object : IdProvider<UserId> by SnowflakeIdFactory(::UserId)
}