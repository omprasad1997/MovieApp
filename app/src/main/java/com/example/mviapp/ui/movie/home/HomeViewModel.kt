package com.example.mviapp.ui.movie.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mviapp.data.model.HomeSection
import com.example.mviapp.data.model.Movie
import com.example.mviapp.data.repository.MovieRepository
import com.example.mviapp.data.util.CuratedKeywords
import dagger.hilt.android.lifecycle.HiltViewModel
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
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    private val _effect = MutableSharedFlow<HomeEffect>()
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


    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.LoadTrending -> loadTrendingMovies()
            is HomeIntent.SelectMovie ->
                emitEffect(HomeEffect.NavigateToMovieDetails(intent.movie.id))
            HomeIntent.ClearError -> {
                _state.update { it.copy(error = null) }
            }
            is HomeIntent.ToggleFavourite -> {
                toggleFavourite(intent.movie) // ✅ ADD
            }
        }
    }

    private fun loadTrendingMovies() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            try {
                val sections = CuratedKeywords.all().mapNotNull { (title, keyword) ->
                    val movies = repository.searchMovies(keyword)
                    if (movies.isEmpty()) {
                        null // skip noisy results
                    } else {
                        HomeSection(title, movies.take(20)) // limit results
                    }
                }

                _state.update {
                    it.copy(
                        isLoading = false,
                        sections = sections
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }

    private fun emitEffect(effect: HomeEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    fun isFavourite(imdbId: String): StateFlow<Boolean> {
        return repository.isFavourite(imdbId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false
            )
    }

    fun toggleFavourite(movie: Movie) {
        viewModelScope.launch {
            val isFav = repository.isFavourite(movie.id). first()
            if (isFav) {
                repository.removeFromFavourites(movie.id)
                emitEffect(HomeEffect.ShowSnackbar("Removed from favourites"))
            } else {
                repository.addToFavourites(movie)
                emitEffect(HomeEffect.ShowSnackbar("Added to favourites"))
            }
        }
    }
}
