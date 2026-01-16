package com.example.mviapp.ui.movie.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mviapp.data.model.Movie
import com.example.mviapp.data.repository.MovieRepository
import com.example.mviapp.ui.movie.home.HomeIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state

    private val _effect = MutableSharedFlow<SearchEffect>()
    val effect = _effect.asSharedFlow()

    init {
        observeFavourites()
    }

    private fun observeFavourites() {
        viewModelScope.launch {
            repository.getFavouriteIds().collect { ids ->
                _state.update {
                    it.copy(favouriteIds = ids)
                }
            }
        }
    }

    fun isFavourite(imdbId: String): StateFlow<Boolean> =
        repository.isFavourite(imdbId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)


    private var searchJob: Job? = null

    fun handleIntent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.SearchMovie -> onSearch(intent.query)
            is SearchIntent.SelectMovie ->
                emitEffect(SearchEffect.NavigateToMovieDetails(intent.imdbId))
            is SearchIntent.ToggleFavourite -> {
                toggleFavourite(intent.movie)
            }
        }
    }

    private fun onSearch(query: String) {
        searchJob?.cancel()

        if (query.isBlank()) {
            _state.update { it.copy(movies = emptyList(), error = null) }
            return
        }

        searchJob = viewModelScope.launch {
            delay(500)
            searchMovies(query)
        }
    }

    private suspend fun searchMovies(query: String) {
        _state.update { it.copy(isLoading = true, error = null) }

        try {
            val movies = repository
                .searchMovies(query)
                .distinctBy { it.id }

            _state.update {
                it.copy(isLoading = false, movies = movies)
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(isLoading = false, error = e.message)
            }
        }
    }

    private fun emitEffect(effect: SearchEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    private fun toggleFavourite(movie: Movie) {
        viewModelScope.launch {
            val isFav = repository.isFavourite(movie.id).first()
            if (isFav) {
                repository.removeFromFavourites(movie.id)
            } else {
                repository.addToFavourites(movie)
            }
        }
    }

}
