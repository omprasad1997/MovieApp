package com.example.mviapp.ui.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mviapp.data.repository.MovieRepository
import com.example.mviapp.mvi.MovieScreenEffect
import com.example.mviapp.mvi.MovieScreenIntent
import com.example.mviapp.mvi.MovieScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    // ---------- STATE ----------
    private val _state = MutableStateFlow(MovieScreenState())
    val state: StateFlow<MovieScreenState> = _state

    // ---------- EFFECT ----------
    private val _effect = MutableSharedFlow<MovieScreenEffect>()
    val effect = _effect.asSharedFlow()

    // ---------- DEBOUNCE ----------
    private var searchJob: Job? = null

    // ---------- INTENT ----------
    fun handleIntent(intent: MovieScreenIntent) {
        when (intent) {
            is MovieScreenIntent.SearchMovie -> onSearch(intent.query)
            is MovieScreenIntent.SelectMovie -> onMovieSelected(intent.imdbId)
            MovieScreenIntent.LoadMovieDetails -> loadMovieDetails()
            MovieScreenIntent.BackClicked -> emitEffect(MovieScreenEffect.NavigateBack)
        }
    }

    // ---------- SEARCH ----------
    private fun onSearch(query: String) {
        searchJob?.cancel()

        if (query.isBlank()) {
            _state.update {
                it.copy(movies = emptyList(), error = null)
            }
            return
        }

        searchJob = viewModelScope.launch {
            delay(500) // debounce
            searchMovies(query)
        }
    }

    private suspend fun searchMovies(query: String) {
        _state.update { it.copy(isSearchLoading = true, error = null) }

        try {
            val movies = repository
                .searchMovies(query)
                .distinctBy { it.id }

            _state.update {
                it.copy(
                    isSearchLoading = false,
                    movies = movies,
                    error = null
                )
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    isSearchLoading = false,
                    error = e.message
                )
            }
        }
    }

    // ---------- MOVIE DETAILS ----------
    private fun onMovieSelected(imdbId: String) {
        _state.update {
            it.copy(
                selectedImdbId = imdbId,
                movieDetails = null
            )
        }
        emitEffect(MovieScreenEffect.NavigateToMovieDetails)
    }

    private fun loadMovieDetails() {
        val imdbId = _state.value.selectedImdbId ?: return

        viewModelScope.launch {
            _state.update { it.copy(isDetailsLoading = true, error = null) }

            try {
                val details = repository.getMovieDetails(imdbId)
                _state.update {
                    it.copy(
                        isDetailsLoading = false,
                        movieDetails = details,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isDetailsLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }

    // ---------- EFFECT ----------
    private fun emitEffect(effect: MovieScreenEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}

