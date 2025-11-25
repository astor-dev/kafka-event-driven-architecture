package com.astordev.ugc.adapter.originpost

import com.astordev.ugc.post.model.Post

fun OriginalPostMessagePayload.toModel(): Post  {
    return Post.from(
            id,
            title,
            content,
            userId,
            categoryId,
            createdAt,
            updatedAt,
            deletedAt
    )
}


