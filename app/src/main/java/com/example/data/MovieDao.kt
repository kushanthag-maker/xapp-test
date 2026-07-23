package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM saved_movies WHERE isWatchlist = 1 ORDER BY id DESC")
    fun getWatchlistMovies(): Flow<List<SavedMovieEntity>>

    @Query("SELECT * FROM saved_movies WHERE downloadState != 'NOT_DOWNLOADED'")
    fun getDownloadedOrDownloadingMovies(): Flow<List<SavedMovieEntity>>

    @Query("SELECT * FROM saved_movies WHERE downloadState = 'COMPLETED'")
    fun getCompletedDownloads(): Flow<List<SavedMovieEntity>>

    @Query("SELECT * FROM saved_movies WHERE id = :movieId LIMIT 1")
    fun getSavedMovie(movieId: String): Flow<SavedMovieEntity?>

    @Query("SELECT * FROM saved_movies WHERE id = :movieId LIMIT 1")
    suspend fun getSavedMovieDirect(movieId: String): SavedMovieEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertSavedMovie(movie: SavedMovieEntity)

    @Query("UPDATE saved_movies SET isWatchlist = :isWatchlist WHERE id = :movieId")
    suspend fun updateWatchlistStatus(movieId: String, isWatchlist: Boolean)

    @Query("UPDATE saved_movies SET downloadState = :state, downloadProgress = :progress, downloadSpeedMb = :speed, downloadedSizeMb = :downloadedSize WHERE id = :movieId")
    suspend fun updateDownloadProgress(movieId: String, state: String, progress: Float, speed: Float, downloadedSize: Long)

    @Query("DELETE FROM saved_movies WHERE id = :movieId AND isWatchlist = 0 AND downloadState = 'NOT_DOWNLOADED'")
    suspend fun deleteMovieIfUnused(movieId: String)

    @Query("SELECT * FROM user_reviews WHERE movieId = :movieId ORDER BY timestamp DESC")
    fun getReviewsForMovie(movieId: String): Flow<List<UserReviewEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReview(review: UserReviewEntity)
}
