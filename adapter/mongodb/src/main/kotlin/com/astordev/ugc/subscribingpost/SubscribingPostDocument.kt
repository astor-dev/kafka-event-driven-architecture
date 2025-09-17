package com.astordev.ugc.subscribingpost

import com.astordev.ugc.post.model.Post
import com.astordev.ugc.post.model.PostId
import com.astordev.ugc.user.model.UserId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collation = "subscribingInboxPosts")
data class SubscribingPostDocument (
    @Id
    val id: String, // postId + followerId 조합
    val postId: Long,
    val postCreatedAt: LocalDateTime,
    val addedAt: LocalDateTime,
    val read: Boolean
) {
    companion object {
        fun generate(
            post: Post,
            followerUserId: UserId
        ): SubscribingPostDocument {
            return SubscribingPostDocument(
                generateDocumentId(post.id, followerUserId),
                post.id.long,
                post.createdAt,
                LocalDateTime.now(),
                false
            )
        }

        private fun generateDocumentId(postId: PostId, followerUserId: UserId): String {
            return "${postId.long}_${followerUserId.long}"
        }
    }

}