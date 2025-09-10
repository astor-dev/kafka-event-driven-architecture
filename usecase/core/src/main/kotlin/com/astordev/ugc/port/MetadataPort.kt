package com.astordev.ugc.port

import com.astordev.ugc.Result
import com.astordev.ugc.category.model.CategoryId
import com.astordev.ugc.user.model.UserId

interface MetadataPort {
    fun getCategoryNameByCategoryId(categoryId: CategoryId): Result<String, GetCategoryError>
    fun getUserNameByUserId(userId: UserId): Result<String, GetUserError>
    fun listFollowerIdsByUserId(userId: UserId): List<Long>

    sealed class GetCategoryError {
        object CategoryNotFound: GetCategoryError()
    }

    sealed class GetUserError {
        object UserNotFound: GetUserError()
    }
}