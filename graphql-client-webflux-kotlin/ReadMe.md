
# GrapqhQL Cleint 

* Kotlin 1.5.1
* Spring-Webflux
*

### DGS Framework(Netflix)
* 공식 문서 : https://netflix.github.io/dgs/


### Query Generation

* gradle task 추가.
```
// GenerateJavaTask
tasks.withType<com.netflix.graphql.dgs.codegen.gradle.GenerateJavaTask> {
    schemaPaths = mutableListOf("$projectDir/src/main/resources/schema/")
    packageName = "com.humanitas.graphql.client"
    typeMapping = mutableMapOf("Long" to "java.lang.Long")
    generateClient = true
}
```

* mutation에 Graphql 예약어가 들어가 있는경우 CodeGen이 안되는 현상 발생.
```graphql
type Mutation {
  insertEquipment(
    id: ID
    used_by: String
    count: Int
    new_or_used: String
  ): Equipment
  editEquipment(
    id: ID
    userd_by: String
    count: Int
    new_or_used: String
  ): Equipment
  deleteEquipment(id: ID): Equipment
    # 뮤테이션 postPerson의 매기변수에 input이라는 키워드가 있으면, 
    # generateJava 태스크 실행 시 에러가 발생한다.
  postPerson(input: PostPersonInput): People!
}
```

### 결과는 GraphQLResponse 객체의 json 필드에 담겨서 나온다.

```kotlin
    override fun executeQuery(
        query: String,
        variables: Map<String, Any>,
        requestExecutor: RequestExecutor
    ): GraphQLResponse {
        return executeQuery(query, variables, null, requestExecutor)
    }
```

```json
{
  "data": {
    "peoplePaginated": [
      {
        "id": "1",
        "first_name": "Alex",
        "last_name": "Davidson",
        "sex": "male",
        "blood_type": "O",
        "serve_years": 2,
        "role": "developer",
        "team": "2",
        "from": "California"
      },
      {
        "id": "2",
        "first_name": "Lindsay",
        "last_name": "West",
        "sex": "female",
        "blood_type": "A",
        "serve_years": 3,
        "role": "designer",
        "team": "3",
        "from": "Arizona"
      }
    ]
  }
}
```



### wiremock-context-initializer

```kotlin
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

```

* stubResponse

```kotlin
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
```

