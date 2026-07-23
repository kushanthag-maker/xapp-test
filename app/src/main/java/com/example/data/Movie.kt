package com.example.data

data class Movie(
    val id: String,
    val title: String,
    val synopsis: String,
    val genre: List<String>,
    val rating: Double,
    val voteCount: Int,
    val duration: String,
    val releaseYear: Int,
    val posterUrl: String,
    val backdropUrl: String,
    val trailerUrl: String = "",
    val director: String,
    val cast: List<CastMember>,
    val ageRating: String = "PG-13",
    val quality: String = "4K Ultra HD",
    val isSinhalaCinema: Boolean = false,
    val fileSizes: Map<String, String> = mapOf(
        "720p HD" to "1.2 GB",
        "1080p Full HD" to "2.4 GB",
        "4K Ultra HD" to "6.8 GB"
    )
)

data class CastMember(
    val name: String,
    val characterName: String,
    val imageUrl: String
)

data class MovieReview(
    val id: String,
    val movieId: String,
    val userName: String,
    val userAvatar: String,
    val rating: Float,
    val comment: String,
    val date: String
)

enum class DownloadState {
    NOT_DOWNLOADED,
    DOWNLOADING,
    PAUSED,
    COMPLETED,
    FAILED
}
