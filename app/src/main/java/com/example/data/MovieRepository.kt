package com.example.data

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MovieRepository(context: Context) {
    private val dao = AppDatabase.getDatabase(context).movieDao()
    private val activeDownloadJobs = mutableMapOf<String, Job>()
    private val repositoryScope = CoroutineScope(Dispatchers.IO)

    fun getAllMovies(): List<Movie> = MockMovies.sampleMovies

    fun getMovieById(id: String): Movie? = MockMovies.sampleMovies.find { it.id == id }

    fun getWatchlist(): Flow<List<SavedMovieEntity>> = dao.getWatchlistMovies()

    fun getDownloads(): Flow<List<SavedMovieEntity>> = dao.getDownloadedOrDownloadingMovies()

    fun getCompletedDownloads(): Flow<List<SavedMovieEntity>> = dao.getCompletedDownloads()

    fun getSavedMovie(id: String): Flow<SavedMovieEntity?> = dao.getSavedMovie(id)

    fun getMovieReviews(movieId: String): Flow<List<UserReviewEntity>> = dao.getReviewsForMovie(movieId)

    suspend fun toggleWatchlist(movieId: String) {
        val movie = getMovieById(movieId) ?: return
        val existing = dao.getSavedMovieDirect(movieId)
        if (existing == null) {
            val entity = SavedMovieEntity(
                id = movie.id,
                title = movie.title,
                posterUrl = movie.posterUrl,
                backdropUrl = movie.backdropUrl,
                rating = movie.rating,
                duration = movie.duration,
                releaseYear = movie.releaseYear,
                genre = movie.genre.joinToString(", "),
                isWatchlist = true
            )
            dao.upsertSavedMovie(entity)
        } else {
            val newWatchlist = !existing.isWatchlist
            dao.updateWatchlistStatus(movieId, newWatchlist)
        }
    }

    suspend fun startDownload(movieId: String, quality: String) {
        val movie = getMovieById(movieId) ?: return
        val existing = dao.getSavedMovieDirect(movieId)
        val sizeString = movie.fileSizes[quality] ?: "2.0 GB"
        val totalMb = parseSizeMb(sizeString)

        val entity = existing?.copy(
            downloadState = DownloadState.DOWNLOADING.name,
            downloadQuality = quality,
            totalSizeMb = totalMb,
            downloadProgress = if (existing.downloadProgress >= 1f) 0f else existing.downloadProgress
        ) ?: SavedMovieEntity(
            id = movie.id,
            title = movie.title,
            posterUrl = movie.posterUrl,
            backdropUrl = movie.backdropUrl,
            rating = movie.rating,
            duration = movie.duration,
            releaseYear = movie.releaseYear,
            genre = movie.genre.joinToString(", "),
            downloadState = DownloadState.DOWNLOADING.name,
            downloadQuality = quality,
            totalSizeMb = totalMb,
            downloadProgress = 0f
        )

        dao.upsertSavedMovie(entity)
        runDownloadSimulation(movie.id, totalMb)
    }

    suspend fun pauseDownload(movieId: String) {
        activeDownloadJobs[movieId]?.cancel()
        activeDownloadJobs.remove(movieId)
        val existing = dao.getSavedMovieDirect(movieId)
        if (existing != null) {
            dao.updateDownloadProgress(
                movieId = movieId,
                state = DownloadState.PAUSED.name,
                progress = existing.downloadProgress,
                speed = 0f,
                downloadedSize = existing.downloadedSizeMb
            )
        }
    }

    suspend fun cancelDownload(movieId: String) {
        activeDownloadJobs[movieId]?.cancel()
        activeDownloadJobs.remove(movieId)
        val existing = dao.getSavedMovieDirect(movieId)
        if (existing != null) {
            if (existing.isWatchlist) {
                dao.updateDownloadProgress(
                    movieId = movieId,
                    state = DownloadState.NOT_DOWNLOADED.name,
                    progress = 0f,
                    speed = 0f,
                    downloadedSize = 0
                )
            } else {
                dao.deleteMovieIfUnused(movieId)
            }
        }
    }

    suspend fun addReview(movieId: String, userName: String, rating: Float, comment: String) {
        val review = UserReviewEntity(
            id = "rev_${System.currentTimeMillis()}",
            movieId = movieId,
            userName = userName.ifBlank { "Movie Fan" },
            rating = rating,
            comment = comment,
            timestamp = System.currentTimeMillis()
        )
        dao.insertReview(review)
    }

    private fun runDownloadSimulation(movieId: String, totalMb: Long) {
        activeDownloadJobs[movieId]?.cancel()
        val job = repositoryScope.launch {
            var currentDownloadedMb = 0L
            val existing = dao.getSavedMovieDirect(movieId)
            if (existing != null && existing.downloadProgress > 0f) {
                currentDownloadedMb = (existing.downloadProgress * totalMb).toLong()
            }

            while (currentDownloadedMb < totalMb) {
                delay(500)
                val speedMbPerSec = (8..18).random().toFloat() + (0..9).random() / 10f
                currentDownloadedMb += (speedMbPerSec * 0.5f).toLong()
                if (currentDownloadedMb > totalMb) currentDownloadedMb = totalMb

                val progress = currentDownloadedMb.toFloat() / totalMb.toFloat()
                val isDone = currentDownloadedMb >= totalMb
                val state = if (isDone) DownloadState.COMPLETED.name else DownloadState.DOWNLOADING.name

                dao.updateDownloadProgress(
                    movieId = movieId,
                    state = state,
                    progress = progress,
                    speed = if (isDone) 0f else speedMbPerSec,
                    downloadedSize = currentDownloadedMb
                )

                if (isDone) break
            }
            activeDownloadJobs.remove(movieId)
        }
        activeDownloadJobs[movieId] = job
    }

    private fun parseSizeMb(sizeStr: String): Long {
        return when {
            sizeStr.contains("GB", ignoreCase = true) -> {
                val num = sizeStr.replace("GB", "").trim().toDoubleOrNull() ?: 2.0
                (num * 1024).toLong()
            }
            sizeStr.contains("MB", ignoreCase = true) -> {
                sizeStr.replace("MB", "").trim().toLongOrNull() ?: 800L
            }
            else -> 2000L
        }
    }
}
