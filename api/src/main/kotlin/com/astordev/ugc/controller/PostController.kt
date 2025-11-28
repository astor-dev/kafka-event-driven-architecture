package com.astordev.ugc.controller

import com.astordev.ugc.*
import com.astordev.ugc.category.model.CategoryId
import com.astordev.ugc.error.converter.extractErrorDetail
import com.astordev.ugc.error.thenThrow
import com.astordev.ugc.model.PostCreateRequest
import com.astordev.ugc.model.PostDetailDto
import com.astordev.ugc.model.PostDto
import com.astordev.ugc.model.PostUpdateRequest
import com.astordev.ugc.post.model.PostId
import com.astordev.ugc.user.model.UserId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts")
class PostController(
    private val postCreateUseCase: PostCreateUseCase,
    private val postReadUseCase: PostReadUseCase,
    private val postUpdateUseCase: PostUpdateUseCase,
    private val postDeleteUseCase: PostDeleteUseCase,
) {
    @PostMapping
    fun createPost(
        @RequestBody request: PostCreateRequest
    ): ResponseEntity<PostDto> {
        val post = postCreateUseCase.create(
            PostCreateUseCase.Request(
                UserId.from(request.userId),
                request.title,
                request.content,
                CategoryId.from(request.categoryId)
            )
        )
        return ResponseEntity.ok().body(PostDto.from(post))
    }

    @PutMapping("/{postId}")
    fun updatePost(@RequestBody request: PostUpdateRequest,
                   @PathVariable("postId") postId: Long
    ): ResponseEntity<PostDto> {
        val updateResult = postUpdateUseCase.update(
            PostUpdateUseCase.Request(
                PostId.from(postId),
                request.title,
                request.content,
                CategoryId.from(request.categoryId)
            )
        )
        val response = updateResult.fold(
            ifLeft = {
                when (it) {
                    is PostUpdateError.PostNotFound -> it.extractErrorDetail().thenThrow()
                }
            },
            ifRight = { ResponseEntity.ok(PostDto.from(it)) }
        )
        return response
    }

    @GetMapping("/{postId}/detail")
    fun readPostDetail(
        @PathVariable("postId") postId: Long
    ): ResponseEntity<PostDetailDto> {
        return postReadUseCase.getById(PostId.from(postId)).fold(
            ifLeft =  { it.extractErrorDetail().thenThrow() },
            ifRight = {
                ResponseEntity.ok(PostDetailDto.from(it))
            }
        )
    }

    @DeleteMapping("{postId}")
    fun deletePost(
        @PathVariable("postId") postId: Long
    ): ResponseEntity<PostDto> {
        val deleteResult = postDeleteUseCase.delete(
            PostDeleteUseCase.Request(
                PostId.from(postId)
            )
        )
        val response = deleteResult.fold(
            ifLeft = {
                when (it) {
                    is PostDeleteError.PostNotFound -> it.extractErrorDetail().thenThrow()
                }
            },
            ifRight = { ResponseEntity.ok(PostDto.from(it)) }
        )
        return response
    }
}