package com.astordev.ugc.postsearch

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository


interface PostSearchRepository : ElasticsearchRepository<PostDocument, Long> {
}