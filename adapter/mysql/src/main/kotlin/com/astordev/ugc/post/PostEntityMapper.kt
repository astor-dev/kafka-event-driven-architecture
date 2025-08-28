package com.astordev.ugc.post

import com.astordev.ugc.category.model.CategoryId
import com.astordev.ugc.post.model.Post
import com.astordev.ugc.post.model.PostId
import com.astordev.ugc.user.model.UserId

fun Post.toEntity(): PostEntity {
    return PostEntity(
        id = this.id.long,
        title = this.title,
        content = this.content,
        userId = this.userId.long,
        categoryId = this.categoryId.long,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        deletedAt = this.deletedAt
    )
}

fun PostEntity.toDomain(): Post {
    return Post.from(
        id = PostId.from(this.id),
        title = this.title,
        content = this.content,
        userId = UserId.from(this.userId),
        categoryId = CategoryId.from(this.categoryId),
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        deletedAt = this.deletedAt
    )
}
