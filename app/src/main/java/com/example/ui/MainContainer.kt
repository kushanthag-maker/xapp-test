package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.Movie
import com.example.ui.components.VideoPlayerView
import com.example.ui.screens.AiRecommendationScreen
import com.example.ui.screens.DownloadsScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MovieDetailsScreen
import com.example.ui.screens.WatchlistScreen
import com.example.ui.theme.CinemaRed
import com.example.viewmodel.MovieViewModel

enum class NavigationTab {
    HOME,
    WATCHLIST,
    DOWNLOADS,
    AI_RECOMMENDATIONS
}

@Composable
fun MainContainer(
    viewModel: MovieViewModel,
    modifier: Modifier = Modifier
) {
    var currentTab by remember { mutableStateOf(NavigationTab.HOME) }
    var viewingMovieDetails by remember { mutableStateOf<Movie?>(null) }

    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedGenre by viewModel.selectedGenre.collectAsState()
    val filteredMovies by viewModel.filteredMovies.collectAsState()
    val watchlist by viewModel.watchlistMovies.collectAsState()
    val downloads by viewModel.downloadList.collectAsState()

    val playingMovie by viewModel.playingMovie.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val playbackProgress by viewModel.playbackProgress.collectAsState()
    val selectedQuality by viewModel.selectedQuality.collectAsState()
    val subtitlesEnabled by viewModel.subtitlesEnabled.collectAsState()

    val aiPrompt by viewModel.aiPrompt.collectAsState()
    val isAiLoading by viewModel.isAiLoading.collectAsState()
    val aiRecommendations by viewModel.aiRecommendationResult.collectAsState()

    val savedMovieForDetails by remember(viewingMovieDetails, downloads, watchlist) {
        mutableStateOf(downloads.find { it.id == viewingMovieDetails?.id })
    }

    Scaffold(
        bottomBar = {
            if (playingMovie == null) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp,
                    modifier = Modifier.testTag("main_bottom_navigation")
                ) {
                    NavigationBarItem(
                        selected = currentTab == NavigationTab.HOME && viewingMovieDetails == null,
                        onClick = {
                            viewingMovieDetails = null
                            currentTab = NavigationTab.HOME
                        },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("Home", fontWeight = FontWeight.SemiBold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = CinemaRed,
                            selectedTextColor = CinemaRed,
                            indicatorColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        modifier = Modifier.testTag("nav_home_tab")
                    )

                    NavigationBarItem(
                        selected = currentTab == NavigationTab.WATCHLIST && viewingMovieDetails == null,
                        onClick = {
                            viewingMovieDetails = null
                            currentTab = NavigationTab.WATCHLIST
                        },
                        icon = { Icon(Icons.Default.Bookmark, contentDescription = "Watchlist") },
                        label = { Text("Watchlist", fontWeight = FontWeight.SemiBold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = CinemaRed,
                            selectedTextColor = CinemaRed,
                            indicatorColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        modifier = Modifier.testTag("nav_watchlist_tab")
                    )

                    NavigationBarItem(
                        selected = currentTab == NavigationTab.DOWNLOADS && viewingMovieDetails == null,
                        onClick = {
                            viewingMovieDetails = null
                            currentTab = NavigationTab.DOWNLOADS
                        },
                        icon = { Icon(Icons.Default.Download, contentDescription = "Downloads") },
                        label = { Text("Downloads", fontWeight = FontWeight.SemiBold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = CinemaRed,
                            selectedTextColor = CinemaRed,
                            indicatorColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        modifier = Modifier.testTag("nav_downloads_tab")
                    )

                    NavigationBarItem(
                        selected = currentTab == NavigationTab.AI_RECOMMENDATIONS && viewingMovieDetails == null,
                        onClick = {
                            viewingMovieDetails = null
                            currentTab = NavigationTab.AI_RECOMMENDATIONS
                        },
                        icon = { Icon(Icons.Default.AutoAwesome, contentDescription = "AI Picks") },
                        label = { Text("AI Assistant", fontWeight = FontWeight.SemiBold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = CinemaRed,
                            selectedTextColor = CinemaRed,
                            indicatorColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        modifier = Modifier.testTag("nav_ai_tab")
                    )
                }
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (viewingMovieDetails != null) {
                val detailsMovie = viewingMovieDetails!!
                MovieDetailsScreen(
                    movie = detailsMovie,
                    onBackClick = { viewingMovieDetails = null },
                    onPlayMovie = { viewModel.playMovie(it) },
                    onStartDownload = { id, quality -> viewModel.startDownload(id, quality) },
                    isWatchlisted = watchlist.any { it.id == detailsMovie.id && it.isWatchlist },
                    onWatchlistToggle = { viewModel.toggleWatchlist(it) },
                    reviewFlow = viewModel.getReviewsForMovie(detailsMovie.id),
                    onSubmitReview = { id, name, rating, comment ->
                        viewModel.submitReview(id, name, rating, comment)
                    },
                    savedMovieState = savedMovieForDetails
                )
            } else {
                when (currentTab) {
                    NavigationTab.HOME -> {
                        HomeScreen(
                            movies = filteredMovies,
                            searchQuery = searchQuery,
                            onQueryChange = { viewModel.setSearchQuery(it) },
                            selectedGenre = selectedGenre,
                            onGenreSelect = { viewModel.setSelectedGenre(it) },
                            onMovieSelect = { movie -> viewingMovieDetails = movie },
                            onPlayMovie = { movie -> viewModel.playMovie(movie) },
                            onStartDownload = { id, quality -> viewModel.startDownload(id, quality) },
                            watchlist = watchlist,
                            onWatchlistToggle = { id -> viewModel.toggleWatchlist(id) }
                        )
                    }

                    NavigationTab.WATCHLIST -> {
                        WatchlistScreen(
                            watchlistEntities = watchlist,
                            allMovies = viewModel.allMovies,
                            onMovieClick = { movie -> viewingMovieDetails = movie },
                            onPlayMovie = { movie -> viewModel.playMovie(movie) },
                            onDownloadClick = { movie -> viewModel.startDownload(movie.id, "1080p Full HD") },
                            onWatchlistToggle = { id -> viewModel.toggleWatchlist(id) }
                        )
                    }

                    NavigationTab.DOWNLOADS -> {
                        DownloadsScreen(
                            downloadList = downloads,
                            allMovies = viewModel.allMovies,
                            onPlayOffline = { movie -> viewModel.playMovie(movie) },
                            onPauseDownload = { id -> viewModel.pauseDownload(id) },
                            onResumeDownload = { id, quality -> viewModel.startDownload(id, quality) },
                            onCancelDownload = { id -> viewModel.cancelDownload(id) }
                        )
                    }

                    NavigationTab.AI_RECOMMENDATIONS -> {
                        AiRecommendationScreen(
                            aiPrompt = aiPrompt,
                            onPromptChange = { viewModel.setAiPrompt(it) },
                            onGenerate = { viewModel.generateAiRecommendations() },
                            isLoading = isAiLoading,
                            recommendations = aiRecommendations,
                            onMovieClick = { movie -> viewingMovieDetails = movie },
                            onPlayMovie = { movie -> viewModel.playMovie(movie) },
                            onDownloadClick = { movie -> viewModel.startDownload(movie.id, "1080p Full HD") }
                        )
                    }
                }
            }

            // Interactive Floating Video Player Overlay
            playingMovie?.let { activeMovie ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    VideoPlayerView(
                        movie = activeMovie,
                        isPlaying = isPlaying,
                        progress = playbackProgress,
                        selectedQuality = selectedQuality,
                        subtitlesEnabled = subtitlesEnabled,
                        onTogglePlayPause = { viewModel.togglePlayPause() },
                        onSeek = { viewModel.setPlaybackProgress(it) },
                        onToggleSubtitles = { viewModel.toggleSubtitles() },
                        onQualitySelected = { viewModel.setSelectedQuality(it) },
                        onClose = { viewModel.closePlayer() },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}
