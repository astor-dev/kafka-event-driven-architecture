package com.astordev.ugc.controller

import com.astordev.ugc.model.PostListDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/list")
class PostListController {

    @GetMapping("/inbox/{userId}")

    fun listSubscribingPosts(
        @PathVariable("userId") userId: Long
    ): ResponseEntity<List<PostListDto>> {
        TODO("NOT_IMPLEMENTED")
    }

    @GetMapping("/search")
    fun search(
        @RequestParam("query") query: String
    ): ResponseEntity<List<PostListDto>> {
        TODO("NOT_IMPLEMENTED")
    }
}