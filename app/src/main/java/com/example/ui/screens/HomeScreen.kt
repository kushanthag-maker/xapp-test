package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.LocalMovies
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Movie
import com.example.data.SavedMovieEntity
import com.example.ui.components.CategoryChipRow
import com.example.ui.components.FeaturedMovieHero
import com.example.ui.components.MovieCard
import com.example.ui.components.SearchBarComponent
import com.example.ui.theme.CinemaRed

@Composable
fun HomeScreen(
    movies: List<Movie>,
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    selectedGenre: String,
    onGenreSelect: (String) -> Unit,
    onMovieSelect: (Movie) -> Unit,
    onPlayMovie: (Movie) -> Unit,
    onStartDownload: (String, String) -> Unit,
    watchlist: List<SavedMovieEntity>,
    onWatchlistToggle: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var downloadTargetMovie by remember { mutableStateOf<Movie?>(null) }
    var selectedQuality by remember { mutableStateOf("1080p Full HD") }

    val categories = listOf("All", "Trending", "Sinhala Cinema", "Action", "Sci-Fi", "Animation", "Drama")
    val featuredMovie = movies.firstOrNull() ?: Movie(
        id = "f1", title = "Featured Movie", synopsis = "", genre = listOf(), rating = 5.0,
        voteCount = 100, duration = "2h", releaseYear = 2025, posterUrl = "", backdropUrl = "",
        director = "", cast = listOf()
    )

    val watchlistIds = remember(watchlist) { watchlist.map { it.id }.toSet() }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp)
        ) {
            // App Title Header Bar
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(CinemaRed, RoundedCornerShape(10.dp))
                                .padding(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Movie,
                                contentDescription = "Logo",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        Column {
                            Text(
                                text = "CineFlix",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 22.sp
                                ),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "Watch & Download Cinema",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Search Bar
            item {
                SearchBarComponent(
                    query = searchQuery,
                    onQueryChange = onQueryChange,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }

            // Category Chips Row
            item {
                CategoryChipRow(
                    categories = categories,
                    selectedCategory = selectedGenre,
                    onCategorySelected = onGenreSelect,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            // Featured Hero Card (Only shown when not searching)
            if (searchQuery.isBlank() && selectedGenre == "All") {
                item {
                    FeaturedMovieHero(
                        movie = featuredMovie,
                        onMovieClick = onMovieSelect,
                        onPlayClick = onPlayMovie,
                        onDownloadClick = { downloadTargetMovie = it },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }

            // Sinhala Cinema Section (if available)
            val sinhalaMovies = movies.filter { it.isSinhalaCinema }
            if (sinhalaMovies.isNotEmpty() && searchQuery.isBlank() && (selectedGenre == "All" || selectedGenre == "Sinhala Cinema")) {
                item {
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "🇱🇰 Sinhala Cinema Hits",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            items(sinhalaMovies) { movie ->
                                MovieCard(
                                    movie = movie,
                                    onMovieClick = onMovieSelect,
                                    onPlayClick = onPlayMovie,
                                    onDownloadClick = { downloadTargetMovie = it },
                                    isWatchlisted = watchlistIds.contains(movie.id),
                                    onWatchlistToggle = onWatchlistToggle
                                )
                            }
                        }
                    }
                }
            }

            // Trending / Catalog List Header
            item {
                Text(
                    text = if (searchQuery.isNotBlank()) "Search Results (${movies.size})" else if (selectedGenre != "All") "$selectedGenre Movies" else "Trending Movies",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            // Horizontal Slider for Trending
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    items(movies) { movie ->
                        MovieCard(
                            movie = movie,
                            onMovieClick = onMovieSelect,
                            onPlayClick = onPlayMovie,
                            onDownloadClick = { downloadTargetMovie = it },
                            isWatchlisted = watchlistIds.contains(movie.id),
                            onWatchlistToggle = onWatchlistToggle
                        )
                    }
                }
            }
        }

        // Quality Picker Modal Dialog for Download
        downloadTargetMovie?.let { targetMovie ->
            AlertDialog(
                onDismissRequest = { downloadTargetMovie = null },
                title = {
                    Text(
                        text = "Download ${targetMovie.title}",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                },
                text = {
                    Column {
                        Text(
                            text = "Select Download Quality:",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        targetMovie.fileSizes.forEach { (qualityOption, size) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedQuality == qualityOption,
                                    onClick = { selectedQuality = qualityOption },
                                    colors = RadioButtonDefaults.colors(selectedColor = CinemaRed)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = qualityOption,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Text(
                                        text = "Estimated size: $size",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            onStartDownload(targetMovie.id, selectedQuality)
                            downloadTargetMovie = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = CinemaRed),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("confirm_download_button")
                    ) {
                        Icon(imageVector = Icons.Default.Download, contentDescription = "Download")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Start Download")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { downloadTargetMovie = null }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
