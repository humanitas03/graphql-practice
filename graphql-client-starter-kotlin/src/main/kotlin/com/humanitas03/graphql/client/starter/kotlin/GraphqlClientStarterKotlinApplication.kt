package com.humanitas03.graphql.client.starter.kotlin

import com.expediagroup.graphql.client.spring.GraphQLWebClient
import kotlinx.coroutines.runBlocking
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GraphqlClientStarterKotlinApplication

fun main(args: Array<String>) {
    runApplication<GraphqlClientStarterKotlinApplication>(*args) {
        println("=========start")
        runBlocking {
            val graphQLClient = GraphQLWebClient("http://localhost:4000/graph")
            val result = graphQLClient.execute(HelloQuery())
            println("====================================================")
            println("hello Result : ${result.data}")
            println("====================================================")
        }
    }
}
