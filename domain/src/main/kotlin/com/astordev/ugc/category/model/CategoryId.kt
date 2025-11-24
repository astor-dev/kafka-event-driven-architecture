package com.astordev.ugc.category.model

import com.astordev.ugc.IdProvider
import com.astordev.ugc.SnowflakeIdFactory

@JvmInline
value class CategoryId private constructor(val long: Long){
    companion object : IdProvider<CategoryId> by SnowflakeIdFactory(::CategoryId)
}