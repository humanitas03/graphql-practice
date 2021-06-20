package com.humanitas.graphql.client.webflux.kotlin.client

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.reactive.function.client.WebClient

class GraphqlWebClient constructor(
    @Value("\${node-server.url}")
    private val nodeServerUrl: String,
    @Autowired
    private var webClient: WebClient,
    @Autowired
    private val objectMapper: ObjectMapper
) {

    fun graphqlServerCall() {
    }
}
