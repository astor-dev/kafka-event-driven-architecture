package com.astordev.ugc.adapter.originpost

import com.astordev.ugc.post.model.Post
import com.astordev.ugc.post.model.PostId

class OriginalPostConverter {
    companion object {
        @JvmStatic
        fun toModel(originalPostMessage: OriginalPostMessage): Post?  {
            return originalPostMessage.payload?.let {
                Post.from(
                    PostId.from(originalPostMessage.id),
                    originalPostMessage.payload.title,
                    originalPostMessage.payload.content,
                    originalPostMessage.payload.userId,
                    originalPostMessage.payload.categoryId,
                    originalPostMessage.payload.createdAt,
                    originalPostMessage.payload.updatedAt,
                    originalPostMessage.payload.deletedAt
                )
            }
        }
    }

}