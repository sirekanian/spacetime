package com.sirekanian.spacetime.data.api.model

import kotlinx.serialization.Serializable

@Serializable
data class NasaMedia(
    val url: String,
    val media_type: String,
)