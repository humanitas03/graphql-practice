package com.humanitas.graphql.client.webflux.kotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GraphqlClientWebfluxKotlinApplication

fun main(args: Array<String>) {
    runApplication<GraphqlClientWebfluxKotlinApplication>(*args)
}
