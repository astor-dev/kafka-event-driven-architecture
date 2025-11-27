package com.astordev.ugc.port

import com.astordev.ugc.category.model.CategoryId
import com.astordev.ugc.user.model.UserId

interface MetadataPort {
    fun getCategoryNameByCategoryId(categoryId: CategoryId): String?
    fun getUserNameByUserId(userId: UserId): String?
    fun listFollowerIdsByUserId(userId: UserId): List<UserId>
}
