package com.astordev.ugc.controller

import com.astordev.ugc.SubscribingPostListUseCase
import com.astordev.ugc.model.PostListDto
import com.astordev.ugc.user.model.UserId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/list")
class PostListController(
    private val subscribingPostListUseCase: SubscribingPostListUseCase
) {

    @GetMapping("/inbox/{userId}")
    fun listSubscribingPosts(
        @PathVariable("userId") userId: Long,
        @RequestParam(name = "page", defaultValue = "0", required = false) page: Int = 0
    ): ResponseEntity<List<PostListDto>> {
        val subscribingInboxPosts = subscribingPostListUseCase.listSubscribingInboxPosts(
            SubscribingPostListUseCase.Request(page, UserId.from(userId))
        )
        return ResponseEntity.ok().body(subscribingInboxPosts.map { PostListDto.from(it) })
    }

    @GetMapping("/search")
    fun search(
        @RequestParam("query") query: String
    ): ResponseEntity<List<PostListDto>> {
        TODO("NOT_IMPLEMENTED")
    }
}