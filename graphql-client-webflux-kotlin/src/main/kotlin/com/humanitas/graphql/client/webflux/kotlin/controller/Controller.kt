package com.humanitas.graphql.client.webflux.kotlin.controller

import com.humanitas.graphql.client.webflux.kotlin.client.GraphqlRestTemlate
import com.humanitas.graphql.client.webflux.kotlin.client.GraphqlWebClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller @Autowired constructor(
    @Autowired
    private val graphqlRestTemlate: GraphqlRestTemlate,
    @Autowired
    private val graphQlWebClient: GraphqlWebClient
) {

    @GetMapping("/resttemplate/test")
    fun test() {
        graphqlRestTemlate.clientCall()
    }

    @GetMapping("/webflux/test")
    fun test2() {
        graphQlWebClient.graphqlServerCall_webflux()
    }
}
