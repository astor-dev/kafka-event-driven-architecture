package com.astordev.ugc

import arrow.core.Either
import arrow.core.raise.context.either
import arrow.core.raise.context.ensure
import arrow.core.raise.context.raise
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
    override fun inspect(post: Post): Either<PostInspectionError, InspectedPost> = either {
        val categoryName = metadataPort.getCategoryNameByCategoryId(post.categoryId)
            ?: raise(PostInspectionError.CategoryNotFound)
        val autoInspectionResult = postAutoInspectPort.inspect(post, categoryName)

        // 3. 검수 결과 확인 (ensure 사용)
        // 조건이 false(BAD)라면, 중괄호 안의 에러(InspectionResultBad)를 반환하고 즉시 종료합니다.
        ensure(autoInspectionResult.status == InspectionStatus.GOOD) {
            PostInspectionError.InspectionResultBad
        }

        // 4. 성공 결과 생성 (여기까지 도달했다면 모두 성공한 것)
        // 마지막 줄의 값이 자동으로 Either.Right로 감싸져서 반환됩니다.
        InspectedPost.generate(
            post,
            categoryName,
            autoInspectionResult.tags
        )
    }
}