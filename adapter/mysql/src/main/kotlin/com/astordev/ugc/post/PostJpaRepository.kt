package com.astordev.ugc.post

import com.astordev.ugc.post.model.PostId
import org.springframework.data.jpa.repository.JpaRepository


interface PostJpaRepository : JpaRepository<PostEntity, Long>