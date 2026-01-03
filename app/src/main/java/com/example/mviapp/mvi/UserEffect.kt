package com.example.mviapp.mvi

sealed class UserEffect {
    object NavigateBack : UserEffect()
    object NavigateToMovieDetails : UserEffect()
}
