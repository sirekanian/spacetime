package com.sirekanian.spacetime.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NasaMedia(
    @SerialName("url") val url: String? = null,
    @SerialName("media_type") val mediaType: String,
)