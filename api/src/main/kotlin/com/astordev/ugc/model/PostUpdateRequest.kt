package com.astordev.ugc.model

data class PostUpdateRequest (
    val title: String,
    val content: String,
    val categoryId: String,
)