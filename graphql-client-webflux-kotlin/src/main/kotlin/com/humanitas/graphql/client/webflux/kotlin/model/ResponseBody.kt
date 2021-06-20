package com.humanitas.graphql.client.webflux.kotlin.model


data class ResponseBody(
    val data: Data
)

data class Data(
    val teams: List<Team>?,
)

data class Team(
    val id: Int,
    val manager: String,
    val supplies: List<Supply>?
)

data class Supply(
    val id: String,
    val team: Int
)
