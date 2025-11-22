package com.astordev.ugc.config

import org.apache.http.HttpHeaders
import org.apache.http.HttpHost
import org.apache.http.HttpResponseInterceptor
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.message.BasicHeader
import org.elasticsearch.client.RestClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories

@EnableElasticsearchRepositories
@Configuration
class ElasticsearchConfig(
    val esProperties: ElasticsearchProperties,
) {
    @Bean
    fun restClient(): RestClient {
        val credentialsProvider = BasicCredentialsProvider()
        return RestClient.builder(HttpHost(esProperties.esHost, esProperties.esPort))
            .setHttpClientConfigCallback { httpClientBuilder ->
                httpClientBuilder.disableAuthCaching()
                httpClientBuilder.setDefaultHeaders(
                    listOf(
                        BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                    )
                )
                httpClientBuilder.addInterceptorLast(HttpResponseInterceptor { response, _ ->
                    response.addHeader("X-Elastic-Product", "Elasticsearch")
                })
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
            }
            .build()
    }
}