package com.example.mviapp.ui.movie.moviedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mviapp.data.model.Movie
import com.example.mviapp.data.model.MovieDetails
import com.example.mviapp.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val imdbId: String =
        savedStateHandle["imdbId"] ?: error("imdbId missing")

    private val _state = MutableStateFlow(MovieDetailsState())
    val state: StateFlow<MovieDetailsState> = _state

    init {
        loadMovieDetails() // CALLED HERE
        observeFavourites()
    }

    private fun observeFavourites() {
        viewModelScope.launch {
            repository.getFavouriteIds().collect { ids ->
                val isFav = ids.contains(imdbId)
                _state.update {
                    it.copy(isFavourite = isFav)
                }
            }
        }
    }


    fun handleIntent(intent: MovieDetailsIntent) {
        when (intent) {
            is MovieDetailsIntent.LoadMovieDetails -> loadMovieDetails()

        }
    }

    private fun loadMovieDetails() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val details = repository.getMovieDetails(imdbId)
                _state.update {
                    it.copy(isLoading = false, movieDetails = details)
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(isLoading = false, error = e.message)
                }
            }
        }
    }

    val isFavourite: StateFlow<Boolean> =
        repository.isFavourite(imdbId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false
            )

    fun toggleFavourite(movie: MovieDetails) {
        viewModelScope.launch {
            if (isFavourite.value) {
                repository.removeFromFavourites(movie.imdbID)
            } else {
                repository.addToFavourites(
                    Movie(
                        id = movie.imdbID,
                        title = movie.title,
                        year = movie.year,
                        poster = movie.poster
                    )
                )
            }
        }
    }

}
