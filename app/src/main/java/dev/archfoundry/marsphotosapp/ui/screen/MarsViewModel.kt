package dev.archfoundry.marsphotosapp.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.archfoundry.marsphotosapp.network.MarsApi
import kotlinx.coroutines.launch
import java.io.IOException


class MarsViewModel : ViewModel() {
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set

    init {
        getMarsPhotos()
    }

    fun getMarsPhotos() {
        viewModelScope.launch {
            try {
                val response = MarsApi.retrofitService.getPhotos()
                marsUiState = MarsUiState.Success("${response.size} fotos desdes la api.")
            } catch (e: IOException) {
                marsUiState = MarsUiState.Error
            }
        }
    }
}


sealed interface MarsUiState {
    data class Success(val photos: String) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}