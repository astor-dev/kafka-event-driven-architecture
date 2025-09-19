package com.astordev.ugc.meta

import com.astordev.ugc.Result
import com.astordev.ugc.category.model.CategoryId
import com.astordev.ugc.port.MetadataPort
import com.astordev.ugc.user.model.UserId
import org.springframework.stereotype.Component

@Component
class MetadataAdapter(
    private val metadataClient: MetadataClient
) : MetadataPort {
    override fun getCategoryNameByCategoryId(categoryId: CategoryId): Result<String, MetadataPort.GetCategoryError> {
        val categoryResponse = metadataClient.getCategoryById(categoryId)
            ?: return Result.Failure(MetadataPort.GetCategoryError.CategoryNotFound)
        return Result.Success(categoryResponse.name)
    }

    override fun getUserNameByUserId(userId: UserId): Result<String, MetadataPort.GetUserError> {
        val userResponse = metadataClient.getUserById(userId)
            ?: return Result.Failure(MetadataPort.GetUserError.UserNotFound)
        return Result.Success(userResponse.name)
    }

    override fun listFollowerIdsByUserId(userId: UserId): List<UserId> {
        return metadataClient.getFollowerIdsByUserId(userId).map { UserId.from(it) }
    }

}