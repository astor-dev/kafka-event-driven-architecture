package com.astordev.ugc.port

import com.astordev.ugc.post.model.Post
import com.astordev.ugc.post.model.PostId
import com.astordev.ugc.user.model.UserId

interface SubscribingPostPort {
    fun addPostToFollowerInboxes(post: Post, followerUserIds: List<UserId>)
    fun listPostIdsByFollowerUserIdWithPagination(followerUserId: UserId, pageNumber: Int, pageSize: Int): List<PostId>
}