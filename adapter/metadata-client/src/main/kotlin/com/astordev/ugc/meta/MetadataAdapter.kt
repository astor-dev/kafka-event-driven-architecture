package com.astordev.ugc.meta

import com.astordev.ugc.category.model.CategoryId
import com.astordev.ugc.port.MetadataPort
import com.astordev.ugc.user.model.UserId
import org.springframework.stereotype.Component

@Component
class MetadataAdapter(
    private val metadataClient: MetadataClient
) : MetadataPort {
    override fun getCategoryNameByCategoryId(categoryId: CategoryId): String?  {
        return metadataClient.getCategoryById(categoryId)?.let { return it.name }
    }

    override fun getUserNameByUserId(userId: UserId): String? {
        return metadataClient.getUserById(userId)?.let { return it.name }
    }

    override fun listFollowerIdsByUserId(userId: UserId): List<UserId> {
        return metadataClient.getFollowerIdsByUserId(userId).map { UserId.from(it) }
    }

}