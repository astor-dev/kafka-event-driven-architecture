package com.astordev.ugc.post

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity(name = "posts")
data class PostEntity (
    @Id
    var id: Long,
    var title: String,
    var content: String,
    var userId: Long,
    var categoryId: Long,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now(),
    var deletedAt: LocalDateTime?
)