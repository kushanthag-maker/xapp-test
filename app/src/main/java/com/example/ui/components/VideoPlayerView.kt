package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ClosedCaption
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.HighQuality
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.Movie
import com.example.ui.theme.CinemaGoldBright
import com.example.ui.theme.CinemaRed
import kotlinx.coroutines.delay

@Composable
fun VideoPlayerView(
    movie: Movie,
    isPlaying: Boolean,
    progress: Float,
    selectedQuality: String,
    subtitlesEnabled: Boolean,
    onTogglePlayPause: () -> Unit,
    onSeek: (Float) -> Unit,
    onToggleSubtitles: () -> Unit,
    onQualitySelected: (String) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showControls by remember { mutableStateOf(true) }
    var currentProgress by remember(progress) { mutableStateOf(progress) }

    // Auto update progress when playing
    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            while (isPlaying && currentProgress < 1f) {
                delay(1000)
                currentProgress = (currentProgress + 0.005f).coerceAtMost(1f)
                onSeek(currentProgress)
            }
        }
    }

    // Auto-hide controls after 4s
    LaunchedEffect(showControls) {
        if (showControls) {
            delay(4000)
            showControls = false
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .testTag("video_player_container"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { showControls = !showControls }
        ) {
            // Video Frame (Backdrop + Subtitles)
            AsyncImage(
                model = movie.backdropUrl.ifEmpty { movie.posterUrl },
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Dark Dimming overlay when controls visible
            if (showControls) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.55f))
                )
            }

            // Subtitle overlay simulation
            if (subtitlesEnabled) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = if (showControls) 60.dp else 20.dp)
                        .background(Color.Black.copy(alpha = 0.75f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = if (movie.isSinhalaCinema) "නැවතත් අප හමුවන තුරු, මා බලා සිටින්නෙමි..." else "Whatever happens, we face this horizon together.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = CinemaGoldBright,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    )
                }
            }

            // Player Controls Overlay
            androidx.compose.animation.AnimatedVisibility(
                visible = showControls,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Top Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            IconButton(
                                onClick = onClose,
                                modifier = Modifier.testTag("close_player_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Close Player",
                                    tint = Color.White
                                )
                            }
                            Column {
                                Text(
                                    text = movie.title,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White,
                                    maxLines = 1
                                )
                                Text(
                                    text = "$selectedQuality • Streaming Preview",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        Row {
                            IconButton(onClick = onToggleSubtitles) {
                                Icon(
                                    imageVector = Icons.Default.ClosedCaption,
                                    contentDescription = "Subtitles",
                                    tint = if (subtitlesEnabled) CinemaGoldBright else Color.White
                                )
                            }
                            IconButton(onClick = {
                                val nextQuality = when (selectedQuality) {
                                    "720p HD" -> "1080p Full HD"
                                    "1080p Full HD" -> "4K Ultra HD"
                                    else -> "720p HD"
                                }
                                onQualitySelected(nextQuality)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.HighQuality,
                                    contentDescription = "Quality",
                                    tint = Color.White
                                )
                            }
                        }
                    }

                    // Center Transport Controls
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { currentProgress = (currentProgress - 0.05f).coerceAtLeast(0f) },
                            modifier = Modifier.size(44.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.FastRewind,
                                contentDescription = "Skip back 10s",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(20.dp))

                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .background(CinemaRed, CircleShape)
                                .clickable { onTogglePlayPause() }
                                .testTag("play_pause_video_button"),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (isPlaying) "Pause" else "Play",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(20.dp))

                        IconButton(
                            onClick = { currentProgress = (currentProgress + 0.05f).coerceAtMost(1f) },
                            modifier = Modifier.size(44.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.FastForward,
                                contentDescription = "Skip forward 10s",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }

                    // Bottom Seek Bar
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Slider(
                            value = currentProgress,
                            onValueChange = {
                                currentProgress = it
                                onSeek(it)
                            },
                            colors = SliderDefaults.colors(
                                thumbColor = CinemaRed,
                                activeTrackColor = CinemaRed,
                                inactiveTrackColor = Color.White.copy(alpha = 0.3f)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            val totalSec = 7200 // 2 hours
                            val currentSec = (currentProgress * totalSec).toInt()
                            val curMin = currentSec / 60
                            val curSecMod = currentSec % 60
                            Text(
                                text = String.format("%02d:%02d", curMin, curSecMod),
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White
                            )
                            Text(
                                text = "2:00:00",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }
        }
    }
}
