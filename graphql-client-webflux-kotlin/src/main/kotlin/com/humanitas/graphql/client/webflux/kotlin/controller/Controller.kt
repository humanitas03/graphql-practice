package com.humanitas.graphql.client.webflux.kotlin.controller

import com.humanitas.graphql.client.webflux.kotlin.client.GraphqlRestTemlate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller @Autowired constructor(
    private val client: GraphqlRestTemlate
) {

    @GetMapping("/test")
    fun test() {
        client.clientCall()
    }
}
