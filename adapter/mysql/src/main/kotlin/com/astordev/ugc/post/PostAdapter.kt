package com.astordev.ugc.post

import com.astordev.ugc.port.PostPort
import com.astordev.ugc.post.model.Post
import com.astordev.ugc.post.model.PostId
import org.springframework.stereotype.Component
@Component
class PostAdapter(
    val postJpaRepository: PostJpaRepository
): PostPort {
    override fun save(post: Post): Post {
        return postJpaRepository.save(post.toEntity()).toDomain()
    }

    override fun findById(id: PostId): Post? {
        return postJpaRepository.findById(id.long).orElse(null)?.toDomain()
    }

    override fun listByIds(ids: List<PostId>): List<Post> {
        return postJpaRepository.findAllById(ids.map { it.long }).map { it.toDomain() }
    }


}