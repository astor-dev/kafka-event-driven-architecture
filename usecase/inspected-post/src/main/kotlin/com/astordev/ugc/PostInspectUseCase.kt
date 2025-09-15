package com.astordev.ugc

import com.astordev.ugc.inspectedpost.model.InspectedPost
import com.astordev.ugc.post.model.Post


interface PostInspectUseCase {
    fun inspect(post: Post): Result<InspectedPost, PostInspectionError>

    sealed class PostInspectionError {
        object CategoryNotFound: PostInspectionError()
    }

}