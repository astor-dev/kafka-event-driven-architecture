package com.astordev.ugc.controller

import com.astordev.ugc.SubscribingPostListUseCase
import com.astordev.ugc.error.converter.extractErrorDetail
import com.astordev.ugc.model.PostListDto
import com.astordev.ugc.user.model.UserId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

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
        return subscribingPostListUseCase.listSubscribingInboxPosts(
            SubscribingPostListUseCase.Request(page, UserId.from(userId))
        ).fold(
            ifLeft = {
                val errorDetail = it.extractErrorDetail()
                throw ResponseStatusException(
                    errorDetail.httpStatus,
                    errorDetail.message
                )
            },
            ifRight = {
                ResponseEntity.ok().body(it.map(PostListDto::from))
            }
        )
    }

    @GetMapping("/search")
    fun search(
        @RequestParam("query") query: String
    ): ResponseEntity<List<PostListDto>> {
        TODO("NOT_IMPLEMENTED")
    }
}