package com.astordev.ugc.adapter.originpost

import com.astordev.ugc.post.model.Post
import com.astordev.ugc.post.model.PostId

fun OriginalPostMessage.toModel(): Post  {
    return Post.from(
            PostId.from(id),
            payload.title,
            payload.content,
            payload.userId,
            payload.categoryId,
            payload.createdAt,
            payload.updatedAt,
            payload.deletedAt
    )
}


