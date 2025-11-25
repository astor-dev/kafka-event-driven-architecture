package com.astordev.ugc.adapter.originpost

import com.astordev.ugc.category.model.CategoryId
import com.astordev.ugc.post.model.PostId
import com.astordev.ugc.user.model.UserId
import java.time.LocalDateTime


data class OriginalPostMessagePayload(
    val id: PostId,
    val title: String,
    val content: String,
    val userId: UserId,
    val categoryId: CategoryId,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val deletedAt: LocalDateTime?
)