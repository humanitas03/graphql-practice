package com.humanitas03.graphql.client.starter.kotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GraphqlClientStarterKotlinApplication

fun main(args: Array<String>) {
    runApplication<GraphqlClientStarterKotlinApplication>(*args) {
        println("=========start")

    }
}
