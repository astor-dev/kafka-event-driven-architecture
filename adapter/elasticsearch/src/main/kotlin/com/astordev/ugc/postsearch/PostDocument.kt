package com.astordev.ugc.postsearch

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.DateFormat
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.time.LocalDateTime

@Document(indexName = "post-1")
data class PostDocument(
    @Id
    val id: Long,
    val title: String,
    val content: String,
    val categoryName: String,
    val tags: List<String>,
    @Field(type = FieldType.Date, format = [DateFormat.date_hour_minute_second_millis])
    val indexedAt: LocalDateTime,
)