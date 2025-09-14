package com.astordev.ugc.controller

import com.astordev.ugc.PostCreateUseCase
import com.astordev.ugc.PostDeleteUseCase
import com.astordev.ugc.PostReadUseCase
import com.astordev.ugc.PostUpdateUseCase
import com.astordev.ugc.category.model.CategoryId
import com.astordev.ugc.model.PostCreateRequest
import com.astordev.ugc.model.PostDetailDto
import com.astordev.ugc.model.PostDto
import com.astordev.ugc.model.PostUpdateRequest
import com.astordev.ugc.post.model.PostId
import com.astordev.ugc.user.model.UserId
import com.astordev.ugc.util.mapBoth
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

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
        val response = updateResult.mapBoth(
            onSuccess = { ResponseEntity.ok(PostDto.from(it)) },
            onFailure = {
                when (it) {
                    is PostUpdateUseCase.Error.PostNotFound ->
                        throw ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found")
                }
            }
        )
        return response
    }

    @GetMapping("/{postId}/detail")
    fun readPostDetail(
        @PathVariable("postId") postId: Long
    ): ResponseEntity<PostDetailDto> {
        val resolvedPost = postReadUseCase.getById(PostId.from(postId))
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        return ResponseEntity.ok().body(PostDetailDto.from(resolvedPost))
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
        val response = deleteResult.mapBoth(
            onSuccess = { ResponseEntity.ok(PostDto.from(it)) },
            onFailure = {
                when (it) {
                    is PostDeleteUseCase.Error.PostNotFound ->
                        throw ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found")
                }
            }
        )
        return response
    }
}