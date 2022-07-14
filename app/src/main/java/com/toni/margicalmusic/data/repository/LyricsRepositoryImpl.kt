package com.toni.margicalmusic.data.repository

import com.toni.margicalmusic.R
import com.toni.margicalmusic.data.dto.lyrics.LyricsRequestDto
import com.toni.margicalmusic.data.services.LyricsService
import com.toni.margicalmusic.domain.models.Lyric
import com.toni.margicalmusic.domain.repositories.LyricsRepository
import com.toni.margicalmusic.utils.AppDispatchers
import com.toni.margicalmusic.utils.ResponseState
import com.toni.margicalmusic.utils.UiText
import javax.inject.Inject

class LyricsRepositoryImpl @Inject constructor(
    private val lyricsService: LyricsService, val appDispatchers: AppDispatchers
) : LyricsRepository {

    override suspend fun fetchLyrics(title: String, artistName: String): ResponseState<Lyric> =
        try {
            val response = lyricsService.getLyrics(
                LyricsRequestDto(
                    artist = artistName, song = title
                )
            )
            if (response.status == "00") {
                ResponseState.Success(response.toLyricModel())
            } else {
                ResponseState.Error(UiText.DynamicText(response.message!!))
            }

        } catch (e: Exception) {
            if (e.message != null) ResponseState.Error(UiText.DynamicText(e.message!!))
            else ResponseState.Error(UiText.StaticText(R.string.lyrics_error))
        }
}