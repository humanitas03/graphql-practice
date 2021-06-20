package com.humanitas.graphql.client.webflux.kotlin.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateFactory {

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}