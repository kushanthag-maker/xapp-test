package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.DownloadState
import com.example.data.Movie
import com.example.data.MovieRepository
import com.example.data.SavedMovieEntity
import com.example.data.UserReviewEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MovieRepository(application)

    val allMovies: List<Movie> = repository.getAllMovies()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedGenre = MutableStateFlow("All")
    val selectedGenre: StateFlow<String> = _selectedGenre.asStateFlow()

    private val _selectedMovie = MutableStateFlow<Movie?>(allMovies.firstOrNull())
    val selectedMovie: StateFlow<Movie?> = _selectedMovie.asStateFlow()

    val watchlistMovies: StateFlow<List<SavedMovieEntity>> = repository.getWatchlist()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val downloadList: StateFlow<List<SavedMovieEntity>> = repository.getDownloads()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val completedDownloads: StateFlow<List<SavedMovieEntity>> = repository.getCompletedDownloads()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredMovies: StateFlow<List<Movie>> = combine(_searchQuery, _selectedGenre) { query, genre ->
        allMovies.filter { movie ->
            val matchesQuery = query.isBlank() ||
                    movie.title.contains(query, ignoreCase = true) ||
                    movie.director.contains(query, ignoreCase = true) ||
                    movie.genre.any { it.contains(query, ignoreCase = true) }

            val matchesGenre = when (genre) {
                "All" -> true
                "Sinhala Cinema" -> movie.isSinhalaCinema
                "Trending" -> movie.rating >= 4.8
                else -> movie.genre.any { it.equals(genre, ignoreCase = true) }
            }

            matchesQuery && matchesGenre
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), allMovies)

    // Player State
    private val _playingMovie = MutableStateFlow<Movie?>(null)
    val playingMovie: StateFlow<Movie?> = _playingMovie.asStateFlow()

    private val _isPlaying = MutableStateFlow(true)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _playbackProgress = MutableStateFlow(0.15f)
    val playbackProgress: StateFlow<Float> = _playbackProgress.asStateFlow()

    private val _selectedQuality = MutableStateFlow("1080p Full HD")
    val selectedQuality: StateFlow<String> = _selectedQuality.asStateFlow()

    private val _subtitlesEnabled = MutableStateFlow(true)
    val subtitlesEnabled: StateFlow<Boolean> = _subtitlesEnabled.asStateFlow()

    // AI Recommendations State
    private val _aiPrompt = MutableStateFlow("")
    val aiPrompt: StateFlow<String> = _aiPrompt.asStateFlow()

    private val _aiRecommendationResult = MutableStateFlow<List<Movie>>(emptyList())
    val aiRecommendationResult: StateFlow<List<Movie>> = _aiRecommendationResult.asStateFlow()

    private val _isAiLoading = MutableStateFlow(false)
    val isAiLoading: StateFlow<Boolean> = _isAiLoading.asStateFlow()

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSelectedGenre(genre: String) {
        _selectedGenre.value = genre
    }

    fun selectMovie(movie: Movie) {
        _selectedMovie.value = movie
    }

    fun toggleWatchlist(movieId: String) {
        viewModelScope.launch {
            repository.toggleWatchlist(movieId)
        }
    }

    fun startDownload(movieId: String, quality: String) {
        viewModelScope.launch {
            repository.startDownload(movieId, quality)
        }
    }

    fun pauseDownload(movieId: String) {
        viewModelScope.launch {
            repository.pauseDownload(movieId)
        }
    }

    fun cancelDownload(movieId: String) {
        viewModelScope.launch {
            repository.cancelDownload(movieId)
        }
    }

    fun playMovie(movie: Movie) {
        _playingMovie.value = movie
        _isPlaying.value = true
        _playbackProgress.value = 0.05f
    }

    fun closePlayer() {
        _playingMovie.value = null
    }

    fun togglePlayPause() {
        _isPlaying.value = !_isPlaying.value
    }

    fun setPlaybackProgress(progress: Float) {
        _playbackProgress.value = progress.coerceIn(0f, 1f)
    }

    fun toggleSubtitles() {
        _subtitlesEnabled.value = !_subtitlesEnabled.value
    }

    fun setSelectedQuality(quality: String) {
        _selectedQuality.value = quality
    }

    fun getReviewsForMovie(movieId: String): StateFlow<List<UserReviewEntity>> {
        return repository.getMovieReviews(movieId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }

    fun submitReview(movieId: String, userName: String, rating: Float, comment: String) {
        viewModelScope.launch {
            repository.addReview(movieId, userName, rating, comment)
        }
    }

    fun setAiPrompt(prompt: String) {
        _aiPrompt.value = prompt
    }

    fun generateAiRecommendations() {
        val query = _aiPrompt.value.trim()
        if (query.isBlank()) return

        _isAiLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Smart matching logic with local catalog + keyword analysis
                kotlinx.coroutines.delay(1200) // Realistic AI thinking time
                val matches = allMovies.filter { movie ->
                    query.split(" ").any { word ->
                        word.length > 2 && (
                                movie.title.contains(word, ignoreCase = true) ||
                                        movie.synopsis.contains(word, ignoreCase = true) ||
                                        movie.genre.any { it.contains(word, ignoreCase = true) }
                                )
                    } || (query.contains("sinhala", ignoreCase = true) && movie.isSinhalaCinema)
                }

                _aiRecommendationResult.value = if (matches.isNotEmpty()) matches else allMovies.shuffled().take(3)
            } catch (e: Exception) {
                _aiRecommendationResult.value = allMovies.shuffled().take(3)
            } finally {
                _isAiLoading.value = false
            }
        }
    }
}
