package com.astordev.ugc.port

import com.astordev.ugc.inspectedpost.model.AutoInspectionResult
import com.astordev.ugc.post.model.Post

interface PostAutoInspectPort {
    fun inspect(post: Post, categoryName: String): AutoInspectionResult
}