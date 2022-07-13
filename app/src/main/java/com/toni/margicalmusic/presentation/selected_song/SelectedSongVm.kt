package com.toni.margicalmusic.presentation.selected_song

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toni.margicalmusic.R
import com.toni.margicalmusic.domain.usecases.GetSongLyricsUseCase
import com.toni.margicalmusic.utils.ResponseState
import com.toni.margicalmusic.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SelectedSongUiState(
    var isLoading: Boolean = false, var errorMessage: UiText? = null, var lyrics: String? = null
)

@HiltViewModel
class SelectedSongVm @Inject constructor(private val lyricsUseCase: GetSongLyricsUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow(SelectedSongUiState())
    val uiState = _uiState.asStateFlow()


    fun fetchSongDetails(title: String, artistName: String) {
        if (title.isEmpty() || artistName.isEmpty()) {
            _uiState.update {
                it.copy(
                    errorMessage = UiText.StaticText(R.string.song_details_error)
                )
            }
            return
        }

        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val response = lyricsUseCase.invoke(title = title, artistName = artistName)) {
                is ResponseState.Success -> _uiState.update {
                    it.copy(
                        isLoading = false, errorMessage = null, lyrics = response.data.lyrics
                    )
                }

                is ResponseState.Error -> _uiState.update {
                    it.copy(
                        errorMessage = response.uiText, isLoading = false, lyrics = null
                    )
                }
            }
        }
    }
}