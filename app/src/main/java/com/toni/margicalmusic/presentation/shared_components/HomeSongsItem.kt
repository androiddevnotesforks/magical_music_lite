package com.toni.margicalmusic.presentation.shared_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Colors
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.toni.margicalmusic.R
import com.toni.margicalmusic.domain.models.Song
import com.toni.margicalmusic.utils.MediaUtils.convertMillisToMinsSecs
import com.toni.margicalmusic.utils.MediaUtils.getAlbumArtUri


@Composable
fun HomeSongsItem(index: Int, song: Song, icon: Int = R.drawable.ic_more) {
    Box(
        modifier = Modifier.background(color = if (index % 2 == 1) MaterialTheme.colors.background else MaterialTheme.colors.primaryVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(data = getAlbumArtUri(albumId = song.albumId)),
                contentDescription = null,
                modifier = Modifier.size(height = 55.dp, width = 55.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .weight(1.0f, true)
                    .padding(start = 10.dp, end = 10.dp),
            ) {
                song.title?.let {
                    Text(
                        text = it, style = MaterialTheme.typography.h2.copy(
                            color = MaterialTheme.colors.onSurface, fontSize = 16.sp
                        )
                    )
                }

                Row(modifier = Modifier.padding(all = 10.dp)) {
                    Image(
                        painterResource(id = R.drawable.ic_logo),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(height = 20.dp, width = 20.dp)
                            .clip(CircleShape)
                    )

                    Text(
                        text = "${song.artistName}. ${convertMillisToMinsSecs(song.duration.toLong())}",
                        modifier = Modifier.padding(start = 10.dp),
                        style = MaterialTheme.typography.h3.copy(
                            fontSize = 14.sp, color = MaterialTheme.colors.onSurface
                        )
                    )

                }
            }
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface
            )
        }
    }
}