package com.yooni.newsletter.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging.logger
import org.apache.http.config.SocketConfig
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.client.BufferingClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.validation.annotation.Validated
import org.springframework.web.client.RestTemplate
import java.util.concurrent.TimeUnit

@Configuration
@Profile("!test")
class RestTemplateConfiguration(
    @Value("300") private val maxThread: Int
) {
    val refundReadTimeout = 30 * 1000
    val connectionTimeout = 3 * 100
    val socketTimeout = 3 * 1000

    @Bean
    fun refundFactory(): HttpComponentsClientHttpRequestFactory =
        HttpComponentsClientHttpRequestFactory().apply {
            httpClient = HttpClientBuilder
                .create()
                .setConnectionManager(
                    PoolingHttpClientConnectionManager().apply {
                        defaultMaxPerRoute = maxThread
                        maxTotal = maxThread * 3
                    }
                )
                .disableCookieManagement()
                .evictIdleConnections(5000, TimeUnit.MILLISECONDS)
                .setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(socketTimeout).build())
                .build()
            setReadTimeout(refundReadTimeout)
            setConnectTimeout(connectionTimeout)
            setConnectionRequestTimeout(connectionTimeout)
        }

    @Bean
    fun restTemplate(builder: RestTemplateBuilder, objectMapper: ObjectMapper): RestTemplate =
        builder.additionalMessageConverters(MappingJackson2HttpMessageConverter(objectMapper))
            .requestFactory { BufferingClientHttpRequestFactory(refundFactory()) }
            .build()
    // TODO: logging interceptor 추가
}