package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_movies")
data class SavedMovieEntity(
    @PrimaryKey val id: String,
    val title: String,
    val posterUrl: String,
    val backdropUrl: String,
    val rating: Double,
    val duration: String,
    val releaseYear: Int,
    val genre: String, // Comma separated
    val isWatchlist: Boolean = false,
    val downloadState: String = "NOT_DOWNLOADED", // NOT_DOWNLOADED, DOWNLOADING, PAUSED, COMPLETED
    val downloadProgress: Float = 0f, // 0.0 to 1.0
    val downloadQuality: String = "1080p Full HD",
    val downloadedSizeMb: Long = 0,
    val totalSizeMb: Long = 2400,
    val downloadSpeedMb: Float = 0f,
    val localFilePath: String = "",
    val lastWatchedTimestamp: Long = 0,
    val watchProgressMillis: Long = 0
)

@Entity(tableName = "user_reviews")
data class UserReviewEntity(
    @PrimaryKey val id: String,
    val movieId: String,
    val userName: String,
    val rating: Float,
    val comment: String,
    val timestamp: Long
)
