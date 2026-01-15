package com.sirekanian.spacetime.data.api

import com.sirekanian.spacetime.data.api.model.NasaMedia
import com.sirekanian.spacetime.model.Thumbnail
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
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
            .filter { it.mediaType == "image" }
            .mapNotNull { media ->
                if (media.url?.startsWith(baseImageUrl) == true) {
                    val imagePath = media.url.removePrefix(baseImageUrl)
                    Thumbnail(baseThumbnailUrl + imagePath, date)
                } else {
                    null
                }
            }
            .reversed()

}