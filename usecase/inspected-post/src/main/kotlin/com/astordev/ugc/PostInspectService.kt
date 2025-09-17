package com.astordev.ugc

import com.astordev.ugc.inspectedpost.model.InspectedPost
import com.astordev.ugc.inspectedpost.model.InspectionStatus
import com.astordev.ugc.port.MetadataPort
import com.astordev.ugc.port.PostAutoInspectPort
import com.astordev.ugc.post.model.Post
import org.springframework.stereotype.Service

@Service
class PostInspectService(
    private val metadataPort: MetadataPort,
    private val postAutoInspectPort: PostAutoInspectPort

) : PostInspectUseCase {
    override fun inspect(post: Post): Result<InspectedPost, PostInspectUseCase.PostInspectionError> {
        when(val categoryNameResult = metadataPort.getCategoryNameByCategoryId(post.categoryId)) {
            is Result.Success -> {
                val categoryName = categoryNameResult.data
                val autoInspectionResult = postAutoInspectPort.inspect(post, categoryName)
                return when(autoInspectionResult.status) {
                    InspectionStatus.GOOD -> {
                        Result.Success(
                            InspectedPost.generate(
                                post,
                                categoryName,
                                autoInspectionResult.tags
                            )
                        )
                    }

                    InspectionStatus.BAD -> {
                        Result.Failure(PostInspectUseCase.PostInspectionError.InspectionResultBad)
                    }
                }

            }
            is Result.Failure -> {
                return Result.Failure(PostInspectUseCase.PostInspectionError.CategoryNotFound)
            }
        }
    }
}