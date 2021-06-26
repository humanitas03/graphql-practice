package com.humanitas.graphql.client.webflux.kotlin

import com.github.tomakehurst.wiremock.client.WireMock.* // ktlint-disable no-wildcard-imports
import com.humanitas.graphql.client.webflux.kotlin.client.GraphqlRestTemlate
import com.humanitas.graphql.client.webflux.kotlin.client.GraphqlRestTemlate.Companion.GRAPHQL_PATH
import com.humanitas.graphql.client.webflux.kotlin.wiremock.WireMockContextInitializer
import com.humanitas.graphql.client.webflux.kotlin.wiremock.WireMockFactory
import graphql.Assert.assertNotNull
import mu.KotlinLogging
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

private val logger = KotlinLogging.logger {}

@SpringBootTest
@ContextConfiguration(initializers = [WireMockContextInitializer::class])
@ActiveProfiles("test")
// @Disabled
class GraphqlRestTemplateTest @Autowired constructor(
    wireMockFactory: WireMockFactory,
    private val graphqlRestTemlate: GraphqlRestTemlate,
    @Value("\${node-server.url}")
    private val nodeServerUrl: String

) {
    private val wireMockServer = wireMockFactory.wireMock()

    private val testResponse = """
        {
          "data": {
            "teams": [
              {
                "id": 1,
                "manager": "Mandy Warren",
                "supplies": [
                  {
                    "id": "ergonomic mouse",
                    "team": 1
                  },
                  {
                    "id": "mug",
                    "team": 1
                  }
                ]
              }
            ]
          }
        }
    """.trimIndent()

    @AfterEach
    fun afterEach() {
        wireMockServer.resetAll()
    }

    private fun stubResponse(url: String, requestBody: String, responseBody: String, responseStatus: Int = HttpStatus.OK.value()) {
        println("URL >>>>>>> $url")
        val path = url.substringAfter(nodeServerUrl)
        println("URL after >>>>>>> $path")
        wireMockServer.stubFor(
            any(urlEqualTo(path))
                .willReturn(
                    aResponse()
                        .withStatus(responseStatus)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(responseBody)
                )
        )
    }

    private fun setStubResponse(requestBody: String) {
        stubResponse("$nodeServerUrl$GRAPHQL_PATH", requestBody, testResponse)
    }

    @Test
    fun `Graphql 서버로 부터 정상 응답을 받았습니다 `() {
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
        setStubResponse(query)
        val result = graphqlRestTemlate.clientCall()
        logger.info("result : $result")
        assertNotNull(result)
    }
}
