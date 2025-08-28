package com.astordev.ugc.post.model

import com.astordev.ugc.category.model.CategoryId
import com.astordev.ugc.user.model.UserId
import java.time.LocalDateTime

class Post (
    var id: PostId,
    var title: String,
    var content: String,
    var userId: UserId,
    var categoryId: CategoryId,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
    var deletedAt: LocalDateTime?
) {
    companion object {
        fun generate(
            userId: UserId,
            title: String,
            content: String,
            categoryId: CategoryId
        ): Post {
            val now = LocalDateTime.now()
            return Post(PostId.generate(), title, content, userId, categoryId, now, now, null)
        }
    }


    fun update(title: String, content: String, categoryId: CategoryId): Post {
        this.title = title
        this.content = content
        this.categoryId = categoryId
        this.updatedAt = LocalDateTime.now()
        return this
    }
    fun delete(): Post {
        val now = LocalDateTime.now()
        this.updatedAt = now
        this.deletedAt = now
        return this
    }

    fun undelete(): Post {
        this.deletedAt = null
        return this
    }
}