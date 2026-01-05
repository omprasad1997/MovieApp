package com.example.mviapp.mvi

sealed class MovieScreenEffect {
    object NavigateBack : MovieScreenEffect()
    object NavigateToMovieDetails : MovieScreenEffect()
}
