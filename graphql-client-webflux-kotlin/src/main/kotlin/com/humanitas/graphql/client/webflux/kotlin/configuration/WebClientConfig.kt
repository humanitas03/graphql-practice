package com.humanitas.graphql.client.webflux.kotlin.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

    @Bean
    @Primary
    fun webClient(): WebClient {
        return WebClient.builder().build()
    }
}
