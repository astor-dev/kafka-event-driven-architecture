package com.astordev.ugc.post

import org.springframework.data.jpa.repository.JpaRepository


interface PostJpaRepository : JpaRepository<PostEntity, Long>