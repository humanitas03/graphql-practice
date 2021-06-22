import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    id("org.springframework.boot") version "2.5.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.10"
    kotlin("plugin.spring") version "1.5.10"
    // codeGenerator DSL Plugin
    id("com.netflix.dgs.codegen") version "4.6.4"
}

group = "com.humanitas"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
//    kapt("org.springframework.boot:spring-boot-configuration-processor")
//    annotationProcessor()
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    // DGS
    implementation(platform("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:4.0.1"))
    implementation("com.netflix.graphql.dgs:graphql-dgs-webflux-starter")
    implementation("com.netflix.graphql.dgs:graphql-dgs-extended-scalars")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.10")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("io.github.microutils:kotlin-logging:1.12.5")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")

    // wiremock
    testImplementation("com.github.tomakehurst:wiremock-jre8:2.28.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// GenerateJavaTask
tasks.withType<com.netflix.graphql.dgs.codegen.gradle.GenerateJavaTask> {
    schemaPaths = mutableListOf("$projectDir/src/main/resources/schema/")
    packageName = "com.humanitas.graphql.client"
    typeMapping = mutableMapOf("Long" to "java.lang.Long")
    generateClient = true
}
