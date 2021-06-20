package com.humanitas.graphql.client.webflux.kotlin.wiremock

import com.github.tomakehurst.wiremock.WireMockServer
import org.springframework.beans.factory.BeanFactory
import org.springframework.stereotype.Component

@Component
class WireMockFactory(private val beanFactory: BeanFactory) {
    companion object {
        const val WIRE_MOCK = "wireMock"
    }

    fun wireMock() = beanFactory.getBean(
        WIRE_MOCK,
        WireMockServer::class.java
    )
}
