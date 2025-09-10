package com.astordev.ugc.model

data class PostCreateRequest (
    val title: String,
    val content: String,
    val userId: Long,
    val categoryId: Long,
)