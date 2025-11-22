package com.astordev.ugc.config

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "spring.data.elasticsearch")
data class ElasticsearchProperties(
    val esHost: String,
    val esPort: Int,
)