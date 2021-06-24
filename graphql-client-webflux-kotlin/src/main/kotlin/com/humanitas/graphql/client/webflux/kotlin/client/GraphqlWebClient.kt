package com.humanitas.graphql.client.webflux.kotlin.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.humanitas.graphql.client.client.TeamProjectionRoot
import com.humanitas.graphql.client.client.TeamsGraphQLQuery
import com.netflix.graphql.dgs.client.DefaultGraphQLClient
import com.netflix.graphql.dgs.client.HttpResponse
import com.netflix.graphql.dgs.client.MonoRequestExecutor
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.util.*

@Component
class GraphqlWebClient constructor(
    @Value("\${node-server.url}")
    private val nodeServerUrl: String,
    @Autowired
    private var webClient: WebClient,
    @Autowired
    private val objectMapper: ObjectMapper
) {

    fun graphqlServerCall_webflux() {

        val client = DefaultGraphQLClient(nodeServerUrl)

        /** Query Object */
        val generatedQuery = GraphQLQueryRequest(
            TeamsGraphQLQuery.Builder().build(),
            TeamProjectionRoot().id().manager().supplies().id().team()
        )

        /** RestTemplate 을 이용 */
        val requestExecutor = MonoRequestExecutor { url, headers, body ->
            val httpHeaders = HttpHeaders()
            headers.forEach(httpHeaders::put)
            println(headers)

            println(headers.keys.first())

            val result = webClient.post()
                .uri(url)
                .headers { header -> header.addAll(httpHeaders) }
                .header("Content-Type", "application/json")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String::class.java)
                .block()

            Mono.just(HttpResponse(200, result, emptyMap()))
        }

        client.reactiveExecuteQuery(generatedQuery.serialize(), Collections.emptyMap(), requestExecutor)
            .doOnNext { println("do on next : $it") }
            .doOnSuccess { println("do on success : $it") }
            .subscribe()
    }
}

data class Response(
    val status: Int?,
    val body: Mono<String>?,
    val headers: HttpHeaders?,
)
