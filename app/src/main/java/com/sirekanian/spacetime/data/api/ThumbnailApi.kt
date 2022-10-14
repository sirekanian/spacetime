package com.sirekanian.spacetime.data.api

import com.sirekanian.spacetime.data.api.model.NasaMedia
import com.sirekanian.spacetime.model.Thumbnail
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json

private const val baseImageUrl = "https://apod.nasa.gov/"
private const val baseThumbnailUrl = "https://sirekanian.com/"

class ThumbnailApi {

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun getThumbnails(date: LocalDate): List<Thumbnail> =
        httpClient.get("https://sirekanian.com/apod/json/$date.json")
            .body<List<NasaMedia>>()
            .filter { it.media_type == "image" && it.url.startsWith(baseImageUrl) }
            .map { Thumbnail(baseThumbnailUrl + it.url.removePrefix(baseImageUrl), date) }
            .reversed()

}