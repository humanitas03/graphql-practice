package com.humanitas.graphql.client.webflux.kotlin.wiremock

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.event.ContextClosedEvent

// https://betterprogramming.pub/part-i-how-to-unit-test-your-kotlin-springboot-webflux-webclient-that-is-calling-external-api-714ccaa186c
// https://medium.com/swlh/how-to-use-wiremock-with-junit-5-in-kotlin-spring-boot-application-5f7c38ad8565
class WireMockContextInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        val wmServer = WireMockServer(WireMockConfiguration().dynamicPort().withRootDirectory("src/test/resources/wiremock"))
        wmServer.start()

        applicationContext.beanFactory.registerSingleton(WireMockFactory.WIRE_MOCK, wmServer)

        applicationContext.addApplicationListener {
            if (it is ContextClosedEvent) {
                wmServer.stop()
            }
        }

        TestPropertyValues
            .of("wiremock.server.port=${wmServer.port()}")
            .applyTo(applicationContext)
    }
}
