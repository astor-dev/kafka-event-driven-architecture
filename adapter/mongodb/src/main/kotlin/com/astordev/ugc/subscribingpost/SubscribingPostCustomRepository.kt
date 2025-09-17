package com.astordev.ugc.subscribingpost

import com.astordev.ugc.user.model.UserId

interface SubscribingPostCustomRepository {
    fun findByFollowerUserIdWithPagination(followerUserId: UserId, pageNumber: Int, pageSize: Int): List<SubscribingPostDocument>
}