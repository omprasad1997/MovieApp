package com.example.mviapp.ui.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mviapp.data.model.User
import com.example.mviapp.data.repository.MovieRepository
import com.example.mviapp.mvi.UserEffect
import com.example.mviapp.mvi.UserIntent
import com.example.mviapp.mvi.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    // ---------- STATE ----------
    private val _state = MutableStateFlow(UserState())
    val state: StateFlow<UserState> = _state

    // ---------- EFFECT ----------
    private val _effect = MutableSharedFlow<UserEffect>()
    val effect = _effect.asSharedFlow()

    // for debounce
    private var searchJob: Job? = null

    // ---------- INTENT ----------
    fun handleIntent(intent: UserIntent) {
        when (intent) {

            is UserIntent.SelectUser -> {
                val user = User(intent.id, intent.name)

                _state.value = _state.value.copy(
                    user = user
                )

                emitEffect(UserEffect.NavigateToProfile)
            }

            is UserIntent.SearchMovie -> {
                debounceSearch(intent.query)
            }

            is UserIntent.SelectMovie -> {
                _state.value = _state.value.copy(
                    selectedMovie = intent.movie
                )
                emitEffect(UserEffect.NavigateToMovieDetails)
            }

            UserIntent.BackClicked -> {
                emitEffect(UserEffect.NavigateBack)
            }
        }
    }

    private fun debounceSearch(query: String) {
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(500) // ✅ debounce
            searchMovies(query)
        }
    }

    // ---------- BUSINESS LOGIC ----------
    private suspend fun searchMovies(query: String) {
        if (query.isBlank()) return

        _state.value = _state.value.copy(
            isLoading = true,
            error = null
        )

        try {
            val movies = repository.searchMovies(query)
            _state.value = _state.value.copy(
                isLoading = false,
                movies = movies
            )
        } catch (e: Exception) {
            _state.value = _state.value.copy(
                isLoading = false,
                error = e.message ?: "Something went wrong"
            )
        }
    }

    // ---------- EFFECT EMITTER ----------
    private fun emitEffect(effect: UserEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}
