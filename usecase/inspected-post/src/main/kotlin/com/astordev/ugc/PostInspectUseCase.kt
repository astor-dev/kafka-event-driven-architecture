package com.astordev.ugc

import arrow.core.Either
import com.astordev.ugc.inspectedpost.model.InspectedPost
import com.astordev.ugc.post.model.Post


interface PostInspectUseCase {
    fun inspect(post: Post): Either<PostInspectionError, InspectedPost>
}
