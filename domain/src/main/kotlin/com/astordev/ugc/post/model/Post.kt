package com.astordev.ugc.post.model

import com.astordev.ugc.category.model.CategoryId
import com.astordev.ugc.user.model.UserId
import java.time.LocalDateTime

class Post private constructor(
    val id: PostId,
    title: String,
    content: String,
    val userId: UserId,
    categoryId: CategoryId,
    val createdAt: LocalDateTime,
    updatedAt: LocalDateTime,
    deletedAt: LocalDateTime?
) {
    var title: String = title
        private set
    var content: String = content
        private set
    var categoryId: CategoryId = categoryId
        private set
    var updatedAt: LocalDateTime = updatedAt
        private set
    var deletedAt: LocalDateTime? = deletedAt
        private set

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

        fun from(
            id: PostId,
            title: String,
            content: String,
            userId: UserId,
            categoryId: CategoryId,
            createdAt: LocalDateTime,
            updatedAt: LocalDateTime,
            deletedAt: LocalDateTime?
        ): Post {
            return Post(id, title, content, userId, categoryId, createdAt, updatedAt, deletedAt)
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