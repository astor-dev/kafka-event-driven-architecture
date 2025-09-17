package com.astordev.ugc.subscribingpost

import com.astordev.ugc.user.model.UserId
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class SubscribingPostRepositoryImpl(
    private val mongoTemplate: MongoTemplate
) : SubscribingPostCustomRepository {

    override fun findByFollowerUserIdWithPagination(
        followerUserId: UserId,
        pageNumber: Int,
        pageSize: Int
    ): List<SubscribingPostDocument> {
        val query = Query()
            .addCriteria(Criteria.where("followerUserId").`is`(followerUserId.long))
            .with(
                PageRequest.of(
                    pageNumber,
                    pageSize,
                    Sort.by(Sort.Direction.DESC, "postCreatedAt")
                )
            )
        return mongoTemplate.find(query, SubscribingPostDocument::class.java)

    }
}