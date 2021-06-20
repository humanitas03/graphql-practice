package com.humanitas.graphql.client.webflux.kotlin.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.humanitas.graphql.client.webflux.kotlin.model.ResponseBody
import com.netflix.graphql.dgs.client.DefaultGraphQLClient
import com.netflix.graphql.dgs.client.HttpResponse
import com.netflix.graphql.dgs.client.RequestExecutor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.util.* // ktlint-disable no-wildcard-imports

@Component
class GraphqlRestTemlate constructor(
    @Value("\${node-server.url}")
    private val nodeServerUrl: String,
    @Autowired
    private var restTemplate: RestTemplate,
    @Autowired
    private val objectMapper: ObjectMapper
) {

    fun clientCall() {
        val client = DefaultGraphQLClient(nodeServerUrl)

        val query = """
            query{
            	teams {
                id
                manager
                supplies {
                  id
                  team
                }
              }
            }
        """.trimIndent()

        /** RestTemplate 을 이용 */
        val requestExecutor = RequestExecutor { url, headers, body ->
            val httpHeaders = HttpHeaders()
            headers.forEach(httpHeaders::put)
            println(headers)
            val exchange = restTemplate.exchange(
                url,
                HttpMethod.POST,
                HttpEntity(body, httpHeaders),
                String::class.java
            )
            HttpResponse(exchange.getStatusCodeValue(), exchange.getBody())
        }

        val result = client.executeQuery(query, Collections.emptyMap(), requestExecutor)
        val parseResult = objectMapper.readValue(result.json, ResponseBody::class.java)
        println("GrahpQL ReSULT : $parseResult")
    }
}

